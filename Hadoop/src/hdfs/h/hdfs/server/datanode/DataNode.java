package h.hdfs.server.datanode;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.util.AbstractList;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.BlockListAsLongs;
import org.apache.hadoop.hdfs.server.datanode.FSDatasetInterface;
import org.apache.hadoop.hdfs.server.datanode.SecureDataNodeStarter.SecureResources;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.protocol.DatanodeCommand;
import org.apache.hadoop.hdfs.server.protocol.DatanodeProtocol;
import org.apache.hadoop.hdfs.server.protocol.DatanodeRegistration;
import org.apache.hadoop.hdfs.server.protocol.NamespaceInfo;
import org.apache.hadoop.http.HttpServer;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;
import org.apache.hadoop.net.NetUtils;
import org.apache.hadoop.util.Daemon;

/**
 * Datanode的实际操作类, 通过main方法启动调用secureMain() 创建Datanode的过程,
 * 调用Namenode的IPC获取版本信息并注册 Datanode自身启动线程, 循环发送心跳报告并处理返回的命令
 */
public class DataNode implements Runnable {

	/*
	 * 启动Datanode后, 就一直等待Datanode线程停止
	 */
	public static void secureMain(String[] args, SecureResources resources)
			throws IOException {
		DataNode datanode = createDataNode(args, null, resources);
		if (datanode != null) {
			datanode.join();
		}
	}

	/*
	 * 启动DataNode后, 注册DataNode, 启动Datanode为后台线程
	 */
	public static DataNode createDataNode(String args[], Configuration conf,
			SecureResources resources) throws IOException {
		DataNode dn = instantiateDataNode(args, conf, resources);
		runDatanodeDaemon(dn);
		return dn;
	}

	/*
	 * new出Configuration, 得到dfs.data.dir值, 得到数据目录字符串的数组
	 */
	public static final String DATA_DIR_KEY = "dfs.data.dir";

	public static DataNode instantiateDataNode(String args[],
			Configuration conf, SecureResources resources) throws IOException {
		conf = new Configuration();
		String[] dataDirs = conf.getStrings(DATA_DIR_KEY);
		return makeInstance(dataDirs, conf, resources);
	}

	/*
	 * 将数据目录字符串的数组转化为File的List, new出DataNode
	 */
	public static DataNode makeInstance(String[] dataDirs, Configuration conf,
			SecureResources resources) throws IOException {
		ArrayList<File> dirs = new ArrayList<File>();
		return new DataNode(conf, dirs, resources);
	}

	public DatanodeProtocol namenode = null;
	public FSDatasetInterface data = null;
	public DataStorage storage;
	int socketWriteTimeout = 0;
	ThreadGroup threadGroup = null;
	Daemon dataXceiverServer = null;
	public DataBlockScanner blockScanner = null;
	private HttpServer infoServer = null;
	SecureResources secureResources = null;
	public Server ipcServer;
	private Thread dataNodeThread = null;
	private static String dnThreadName;
	public DatanodeRegistration dnRegistration = null;
	volatile boolean shouldRun = true;
	public Daemon blockScannerThread = null;
	long lastHeartbeat = 0;
	long heartBeatInterval;
	long blockReportInterval;
	long lastBlockReport = 0;

	/*
	 * 构造, 启动Datanode
	 */
	DataNode(final Configuration conf, final AbstractList<File> dataDirs,
			SecureResources resources) throws IOException {
		startDataNode(conf, dataDirs, resources);
	}

	/*
	 * 启动Datanode
	 */
	@SuppressWarnings("unused")
	void startDataNode(Configuration conf, AbstractList<File> dataDirs,
			SecureResources resources) throws IOException {
		secureResources = resources;
		InetSocketAddress nameNodeAddr = NameNode.getServiceAddress(conf, true);

		storage = new DataStorage();

		// 启动与Namenode的IPC
		this.namenode = (DatanodeProtocol) RPC.waitForProxy(
				DatanodeProtocol.class, DatanodeProtocol.versionID,
				nameNodeAddr, conf);
		NamespaceInfo nsInfo = handshake();

		// 数据集合
		this.data = new FSDataset(storage, conf);

		// 启动SocketServer用来读写Block流式数据
		InetSocketAddress socAddr = null;
		ServerSocket ss = (socketWriteTimeout > 0) ? ServerSocketChannel.open()
				.socket() : new ServerSocket();
		Server.bind(ss, socAddr, 0);

		// 启动接受Socket流式数据的线程组, 创建流接口服务器DataXceiverServer
		this.threadGroup = new ThreadGroup("dataXceiverServer");
		this.dataXceiverServer = new Daemon(threadGroup, new DataXceiverServer(
				ss, conf, this));
		this.threadGroup.setDaemon(true); // auto destroy when empty

		// 创建块扫描器DataBlockScanner
		blockScanner = new DataBlockScanner(this, (FSDataset) data, conf);

		// 启动HttpServer和SecondaryNamenode通信
		// InetSocketAddress infoSocAddr = null;
		// String infoHost = infoSocAddr.getHostName();
		// int tmpInfoPort = infoSocAddr.getPort();
		// this.infoServer = new HttpServer();
		this.infoServer.start();

		// 启动Datanode之间的IPC调用
		InetSocketAddress ipcAddr = NetUtils.createSocketAddr(conf
				.get("dfs.datanode.ipc.address"));
		ipcServer = RPC.getServer(this, ipcAddr.getHostName(), ipcAddr
				.getPort(), conf.getInt("dfs.datanode.handler.count", 3),
				false, conf, null);
	}

	/*
	 * 调用Namenode的IPC接口versionRequest(), 并做一些检查版本号的工作
	 */
	private NamespaceInfo handshake() throws IOException {
		return namenode.versionRequest();
	}

	/*
	 * 注册Datanode到Namenode, 启动Datanode这个线程
	 */
	public static void runDatanodeDaemon(DataNode dn) throws IOException {
		if (dn != null) {
			dn.register();
			dn.dataNodeThread = new Thread(dn, dnThreadName);
			dn.dataNodeThread.setDaemon(true); // needed for JUnit testing
			dn.dataNodeThread.start();
		}
	}

	/*
	 * 调用Namenode的IPC注册接口
	 */
	private void register() throws IOException {
		dnRegistration = namenode.register(dnRegistration);
	}

	/*
	 * Datanode线程, 启动dataXceiverServer和ipcServer, 然后循环发送IPC心跳和处理命令
	 */
	@Override
	public void run() {
		dataXceiverServer.start();
		ipcServer.start();

		while (shouldRun) {
			try {
				offerService();
			} catch (Exception ex) {
			}
		}

		shutdown();
	}

	/*
	 * 循环发送IPC心跳和上报数据节点接收到的数据块、处理命令
	 */
	public void offerService() throws Exception {
		while (shouldRun) {
			long startTime = System.currentTimeMillis();

			// 该发送心跳了
			if (startTime - lastHeartbeat > heartBeatInterval) {
				lastHeartbeat = startTime;
				DatanodeCommand[] cmds = namenode.sendHeartbeat(dnRegistration,
						data.getCapacity(), data.getDfsUsed(), data
								.getRemaining(), 0, 0);
				if (!processCommand(cmds)) {
					continue;
				}
			}

			// 如果有新的Block入账, 则报告
			Block[] blockArray = null;
			String[] delHintArray = null;
			namenode.blockReceived(dnRegistration, blockArray, delHintArray);

			// 该发送报告了
			if (startTime - lastBlockReport > blockReportInterval) {
				Block[] bReport = data.getBlockReport();
				DatanodeCommand cmd = namenode.blockReport(dnRegistration,
						BlockListAsLongs.convertToArrayLongs(bReport));
				processCommand(cmd);
			}

			//启动块扫描器DataBlockScanner
			if (blockScanner != null && blockScannerThread == null) {
				blockScannerThread = new Daemon(blockScanner);
				blockScannerThread.start();
			}
		}
	}

	/*
	 * 处理心跳发回的一组命令
	 */
	private boolean processCommand(DatanodeCommand[] cmds) {
		for (DatanodeCommand cmd : cmds) {
			try {
				if (processCommand(cmd) == false) {
					return false;
				}
			} catch (IOException ioe) {
			}
		}
		return true;
	}

	/*
	 * 具体处理心跳发回的命令
	 */
	private boolean processCommand(DatanodeCommand cmd) throws IOException {
		return true;
	}

	/*
	 * 关闭Datanode开启的各个资源
	 */
	public void shutdown() {

	}

	/*
	 * 等待Datanode线程结束
	 */
	void join() {
		if (dataNodeThread != null) {
			try {
				dataNodeThread.join();
			} catch (InterruptedException e) {
			}
		}
	}
}

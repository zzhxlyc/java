package h.hdfs.server.protocol;

import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.DatanodeID;
import org.apache.hadoop.hdfs.protocol.LocatedBlock;
import org.apache.hadoop.hdfs.server.protocol.DatanodeCommand;
import org.apache.hadoop.hdfs.server.protocol.DatanodeRegistration;
import org.apache.hadoop.hdfs.server.protocol.NamespaceInfo;
import org.apache.hadoop.hdfs.server.protocol.UpgradeCommand;
import org.apache.hadoop.ipc.VersionedProtocol;

/**
 * Datanode和Namenode之间通信的IPC接口
 * Datanode启动时, 会和Namenode进行握手, 调用versionRequest(), 检查buildVersion是否一致
 * 	然后注册调用register()
 * 	注册成功后, 调用blockReport(), 上报所有数据块信息
 * 除了和升级相关的两个指令(Finalize和UpgradeCommand), 其他都由sendHeartbeat()返回
 */
public interface DatanodeProtocol extends VersionedProtocol {
	
	NamespaceInfo versionRequest();
	
	DatanodeRegistration register(DatanodeRegistration registration);
	
	DatanodeCommand blockReport(DatanodeRegistration registration,
			long[] blocks);
	
	DatanodeCommand[] sendHeartbeat(DatanodeRegistration registration,
            long capacity,
            long dfsUsed, long remaining,
            int xmitsInProgress,	//正在写文件数据的连接数
            int xceiverCount		//读写数据使用的线程数
            );
	
	/*
	 * 向Namenode报告完整接收了一些数据块, 最后一个参数由平衡器使用
	 */
	void blockReceived(DatanodeRegistration registration,
			Block blocks[],
			String[] delHints);
	
	/*
	 * HDFS使用CRC-32进行错误检测, 3种情况进行校验:
	 * 	1.Datanode接收数据后, 存储数据前
	 * 	2.某些情况下，客户端读取数据节点上的数据
	 * 	3.Datanode上的DataBlockScanner扫描线程定期扫描数据块
	 * 
	 * 上报LocatedBlock信息, Namenode即会移除错误块
	 */
	void reportBadBlocks(LocatedBlock[] blocks);
	
	/*
	 * 和系统异常处理有关; 申请一个新的数据块ID号
	 */
	long nextGenerationStamp(Block block, boolean fromNN);
	
	/*
	 * 和系统异常处理有关; 申请一个新的数据块ID号
	 */
	void blocksBeingWrittenReport(DatanodeRegistration registration,
			long[] blocks);
	
	/*
	 * 和系统异常处理有关; 数据块恢复完成后, 报告执行情况
	 */
	void commitBlockSynchronization(Block block,
		      long newgenerationstamp, long newlength,
		      boolean closeFile, boolean deleteblock, DatanodeID[] newtargets);
	
	/*
	 * 报告Datanode运行过程中的一些问题, 如磁盘挂了/数据块不存在等
	 */
	void errorReport(DatanodeRegistration registration,
			int errorCode, String msg);
	
	/*
	 * 报告升级的状态
	 */
	UpgradeCommand processUpgradeCommand(UpgradeCommand comm);

}

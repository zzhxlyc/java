package h.ipc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Hashtable;

import javax.net.SocketFactory;

import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Writable;

/**
 * 处理RPC客户端
 * 	客户端的Connection在socket建立后作为后台进程start(), 得到结果后线程结束, 周而复始
 * 		Connection线程阻塞在receiveResponse()的in.readInt();中
 * 	客户端的Connection发送使用普通Socket, 接收使用hadoop.net.SocketInputStream(NIO)
 */
public class Client {
	
	/***************** Inner static class Section ****************/
	
	/**
	 * 表示一次TCP连接对方的属性(so也叫remoteId), 网络地址, RPC用户接口类, 时间属性等
	 */
	static class ConnectionId {
		InetSocketAddress address;
		Class<?> protocol;	//RPC用户接口类， extend VersionedProtocol
	}

	/**
	 * 一次RPC传输中的参数值和结果值的封装 参数值为RPC.Invocation类, 
	 * 	对RPC执行函数的参数的class和instance的封装
	 */
	private class Call {
		int id; // call id
		Writable param; // parameter
		Writable value;
		boolean done;

		protected synchronized void callComplete() {
			this.done = true;
			notify(); // notify caller
		}
		public synchronized void setValue(Writable value) {
			this.value = value;
			callComplete();
		}
	}

	/**
	 * 执行一次TCP连接具体的连接类. 
	 * 客户端写数据时, 数据分帧使用"显式长度", 即先写数据长度, 再写入数据
	 */
	private class Connection extends Thread {
		InetSocketAddress server;
		Socket socket = null;
		DataInputStream in;
		DataOutputStream out;

		// 一个connection可能对同一个远程机器发起多个RPC请求, 一个Call代表一个请求
		// Call_id -> Call
		Hashtable<Integer, Call> calls = new Hashtable<Integer, Call>();

		/*
		 * 会进行${ipc.client.connect.max.retries}次数的重试
		 * 使用TcpNoDelay标志, 禁用TCP的Nagle算法, 关闭Socket底层的缓冲, 确保数据及时发送
		 * 		配置项${ipc.client.tcpnodelay}
		 */
		synchronized void setupIOstreams() {
			setupConnection();
			
			writeHeader();	//仅在第一次连接时会握手一次
			
			start();
		}
		synchronized void setupConnection() {}
		private void writeHeader(){}
		public void sendParam(Call call) {}

		public void run() {
			while (waitForWork()) {//wait here for work - read or close connection
				receiveResponse();
			}
		}
		private synchronized boolean waitForWork() {return true;}
		private void receiveResponse() {}
	}

	/***************** Field Section ****************/
	Hashtable<ConnectionId, Connection> connections = 
		new Hashtable<ConnectionId, Connection>();
	Class<? extends Writable> valueClass = ObjectWritable.class;
	// RPC.ClientCache 根据 socketFactory -> client 建立缓存
	SocketFactory socketFactory;

	/***************** Method Section ****************/
	// param为RPC.Invocation
	public Writable call(Writable param, ConnectionId remoteId) {
		return null;
	}

	// 从connections缓存中拿或者new一个, 并将call添加进去
	Connection getConnection(ConnectionId remoteId, Call call) {
		return null;
	}
}

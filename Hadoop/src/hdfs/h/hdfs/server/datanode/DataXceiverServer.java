package h.hdfs.server.datanode;

import java.net.ServerSocket;

import org.apache.hadoop.conf.Configuration;

/**
 * Block数据流式处理器
 */
public class DataXceiverServer implements Runnable {
	
	DataXceiverServer(ServerSocket ss, Configuration conf, 
		      DataNode datanode) {
		
	}

	@Override
	public void run() {
	}

}

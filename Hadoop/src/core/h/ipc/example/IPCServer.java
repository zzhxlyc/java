package h.ipc.example;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;

public class IPCServer {
	
	public static final long VERSION = 0;
	public static final int PORT = 11111;
	
	public static void main(String[] args) {
		try {
			IPCQuery query = new IPCQueryImpl();
			//0.0.0.0表示监听所有地址
			Server server = RPC.getServer(query, 
					"0.0.0.0", PORT, new Configuration());
			server.start();
//			server.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

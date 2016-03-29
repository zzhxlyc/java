package h.ipc.example;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

public class IPCClient {
	
	public static void main(String[] args) {
		try {
			InetSocketAddress address = 
				new InetSocketAddress("localhost", IPCServer.PORT);
			IPCQuery query = (IPCQuery)RPC.getProxy(
											IPCQuery.class, 
											IPCServer.VERSION, 
											address, 
											new Configuration());
			IPCResult result = query.getIPCResult();
			System.out.println(result);
			RPC.stopProxy(query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

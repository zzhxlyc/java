package h.ipc.example;

import org.apache.hadoop.ipc.VersionedProtocol;

public interface IPCQuery extends VersionedProtocol {
	
	IPCResult getIPCResult();
	
}

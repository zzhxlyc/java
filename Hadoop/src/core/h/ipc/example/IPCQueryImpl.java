package h.ipc.example;

import java.io.IOException;

public class IPCQueryImpl implements IPCQuery {
	@Override
	public IPCResult getIPCResult() {
		return new IPCResult();
	}

	@Override
	public long getProtocolVersion(String protocol, long clientVersion)
			throws IOException {
		return IPCServer.VERSION;
	}
}

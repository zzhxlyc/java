package h.ipc.example;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.io.Writable;

public class IPCResult implements Writable {
	private int result;

	public IPCResult() {
		result = new Random(System.currentTimeMillis()).nextInt();
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		result = in.readInt();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(result);
	}
}

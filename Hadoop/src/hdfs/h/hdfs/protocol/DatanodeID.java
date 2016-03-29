package h.hdfs.protocol;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * 唯一标示Datanode的ID, 只要name和storageID相同就算equals
 */
public class DatanodeID implements WritableComparable<DatanodeID> {

	//hostname:port
	public String name;
	//存储标识, 集群中唯一
	public String storageID;
	//www服务的监听端口
	protected int infoPort;
	//ipc的监听端口
	public int ipcPort;

	@Override
	public void readFields(DataInput in) throws IOException {}

	@Override
	public void write(DataOutput out) throws IOException {}

	@Override
	public int compareTo(DatanodeID o) {return 0;}

}

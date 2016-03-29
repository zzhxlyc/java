package h.hdfs.protocol;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.io.Writable;

/**
 * 对已经确定位置信息的Block的封装
 */
@SuppressWarnings("unused")
public class LocatedBlock implements Writable {

	private Block b;
	//离文件第一个字节的距离
	private long offset;
	//拥有这个Block的Datanode信息
	private DatanodeInfo[] locs;
	//是否所有这个Block的备份全部失效了
	private boolean corrupt;

	@Override
	public void readFields(DataInput in) throws IOException {}

	@Override
	public void write(DataOutput out) throws IOException {}

}

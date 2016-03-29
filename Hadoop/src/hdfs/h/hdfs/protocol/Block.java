package h.hdfs.protocol;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * Block的实际文件命名 : blk_{blockId}
 */
@SuppressWarnings("unused")
public class Block implements Writable, Comparable<Block> {
	
	//唯一标示
	private long blockId;
	//字节长度
	private long numBytes;
	//类似最后修改时间, 保证读写一致性
	private long generationStamp;
	
	
	@Override
	public void readFields(DataInput in) throws IOException {}
	@Override
	public void write(DataOutput out) throws IOException {}
	@Override
	public int compareTo(Block o) {return 0;}

}

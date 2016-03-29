package h.hdfs.protocol;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hdfs.protocol.LocatedBlock;
import org.apache.hadoop.io.Writable;

/**
 * 多个顺序LocatedBlock的封装, 一般为一个文件
 */
@SuppressWarnings("unused")
public class LocatedBlocks implements Writable {
	//文件长度
	private long fileLength;
	//按字节顺序组织的LocatedBlock
	private List<LocatedBlock> blocks;
	//文件是否正在构建状态, 比如正在写入中之类的, 为true时, fileLength会随时改变
	private boolean underConstruction;
	
	/*
	 * 搜索字节偏移量offset在哪个block中; 使用的Comparator应该缓存起来 =.= 
	 * 
	 * 算法如下:
	 * 	new一个假的偏移量为offset只有1个字节的Block
	 * 	写一个Comparator, 当这个假的Block被包含时返回0, 不然返回1或-1 
	 * 	利用二分搜索, 在blocks中搜索
	 */
	public int findBlock(long offset) {return 0;}

	@Override
	public void readFields(DataInput in) throws IOException {
	}

	@Override
	public void write(DataOutput out) throws IOException {
	}

}

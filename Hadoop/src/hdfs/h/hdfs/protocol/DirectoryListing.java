package h.hdfs.protocol;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;
import org.apache.hadoop.io.Writable;

/**
 * HDFS中一个目录下的文件和目录信息列表
 */
@SuppressWarnings("unused")
public class DirectoryListing implements Writable {

	private HdfsFileStatus[] partialListing;
	private int remainingEntries;

	public void readFields(DataInput in) throws IOException {
	}

	public void write(DataOutput out) throws IOException {
	}

}

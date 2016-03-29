package h.hdfs.protocol;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.Writable;

/**
 *	HDFS的文件的描述信息, Hadoop不支持高级用户和用户组功能, 只记录字符串
 */
@SuppressWarnings("unused")
public class HdfsFileStatus implements Writable {

	// local name of the inode that's encoded in java in UTF8
	private byte[] path;
	private long length;
	private boolean isdir;
	private short block_replication;
	private long blocksize;
	private long modification_time;
	private long access_time;
	private FsPermission permission;
	private String owner;
	private String group;

	public void readFields(DataInput in) throws IOException {}

	public void write(DataOutput out) throws IOException {}
}

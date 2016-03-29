package h.fs;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.Writable;

/**
 * 文件描述类
 * Hadoop不支持具体的用户和用户组的管理, 简单的记录字符串信息
 */
@SuppressWarnings("unused")
public class FileStatus implements Writable, Comparable<FileStatus> {

	private Path path;
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
	public int compareTo(FileStatus o) {return 0;}

}

package h.hdfs.server.common;


/**
 * 公共信息类
 */
public class StorageInfo {

	//负整数, 保存了HDFS存储系统信息结构的版本号
	public int layoutVersion;
	//存储系统的唯一标识符
	public int namespaceID;
	//该存储系统信息的创建时间
	public long cTime;

}

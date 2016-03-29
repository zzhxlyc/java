package h.hdfs.server.protocol;

import org.apache.hadoop.hdfs.protocol.DatanodeID;
import org.apache.hadoop.hdfs.security.token.block.ExportedBlockKeys;
import org.apache.hadoop.hdfs.server.common.StorageInfo;
import org.apache.hadoop.io.Writable;

/**
 * 被大量的DatanodeProtocol的方法使用
 */
public class DatanodeRegistration extends DatanodeID implements Writable {
	
	//公用信息类
	public StorageInfo storageInfo;
	//安全相关
	public ExportedBlockKeys exportedKeys;

}

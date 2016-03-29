package h.hdfs.server.protocol;

import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.server.protocol.BlockMetaDataInfo;
import org.apache.hadoop.hdfs.server.protocol.BlockRecoveryInfo;
import org.apache.hadoop.ipc.VersionedProtocol;

/**
 * Datanode之间的IPC调用
 */
public interface InterDatanodeProtocol extends VersionedProtocol {
	
	/*
	 * 被维护工具使用, 日常代码没用到
	 */
	BlockMetaDataInfo getBlockMetaDataInfo(Block block);
	
	/*
	 * 用于数据块恢复; 
	 * 处于协调者地位的Datanode, 用于获取参与到数据块恢复过程的各个数据节点上的数据块信息
	 */
	BlockRecoveryInfo startBlockRecovery(Block block);
	
	/*
	 * 用于数据块恢复; 数据块恢复前后的信息
	 */
	void updateBlock(Block oldblock, Block newblock, boolean finalize);

}

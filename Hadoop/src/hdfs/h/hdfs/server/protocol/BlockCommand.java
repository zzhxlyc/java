package h.hdfs.server.protocol;

import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.server.protocol.DatanodeCommand;

/**
 * Namenode向Datanode发送的关于处理Block的命令, 一般用于废弃一些无用Block或者
 * 	复制一些Block到其他Datanode
 */
public class BlockCommand extends DatanodeCommand {
	//具体数据块
	Block blocks[];
	//数据节点信息
	DatanodeInfo targets[][];
}

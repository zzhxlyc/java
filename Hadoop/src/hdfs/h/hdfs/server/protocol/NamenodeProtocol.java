package h.hdfs.server.protocol;

import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.security.token.block.ExportedBlockKeys;
import org.apache.hadoop.hdfs.server.namenode.CheckpointSignature;
import org.apache.hadoop.hdfs.server.protocol.BlocksWithLocations;
import org.apache.hadoop.ipc.VersionedProtocol;

/**
 * Namenode的IPC接口, 被SecondaryNamenode和HDFS工具:balancer使用
 * 	Namenode和SecondaryNamenode之间互相下载镜像使用HTTP的GET方法, 内置HTTP服务器(Jetty)
 */
public interface NamenodeProtocol extends VersionedProtocol {
	
	/*
	 * balancer使用
	 */
	BlocksWithLocations getBlocks(DatanodeInfo datanode, long size);
	
	/*
	 * balancer使用
	 */
	ExportedBlockKeys getBlockKeys();
	
	/*
	 * SecondaryNamenode使用; 获取日志大小, 小无所谓, 大要触发合并
	 */
	long getEditLogSize();
	
	/*
	 * SecondaryNamenode使用; 通知Namenode进行日志合并
	 */
	CheckpointSignature rollEditLog();
	
	/*
	 * SecondaryNamenode使用; 上传完元数据镜像后, 调用, Namenode进行一些必要处理, 完成数据合并
	 */
	void rollFsImage();

}

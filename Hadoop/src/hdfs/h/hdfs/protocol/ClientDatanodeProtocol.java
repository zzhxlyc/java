package h.hdfs.protocol;

import java.io.IOException;

import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.BlockLocalPathInfo;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.LocatedBlock;
import org.apache.hadoop.hdfs.security.token.block.BlockTokenIdentifier;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.hadoop.security.token.Token;

/**
 * Client和DataNode通信的IPC接口
 */
public interface ClientDatanodeProtocol extends VersionedProtocol {

	LocatedBlock recoverBlock(Block block, boolean keepLength,
			DatanodeInfo[] targets) throws IOException;
	
	Block getBlockInfo(Block block) throws IOException;

	BlockLocalPathInfo getBlockLocalPathInfo(Block block,
			Token<BlockTokenIdentifier> token) throws IOException;

}

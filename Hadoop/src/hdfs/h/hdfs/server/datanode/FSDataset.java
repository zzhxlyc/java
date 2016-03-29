package h.hdfs.server.datanode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.BlockLocalPathInfo;
import org.apache.hadoop.hdfs.protocol.FSConstants;
import org.apache.hadoop.hdfs.server.datanode.FSDatasetInterface;
import org.apache.hadoop.hdfs.server.protocol.BlockRecoveryInfo;
import org.apache.hadoop.util.DiskChecker.DiskErrorException;

/**
 * 管理Datanode的Block数据的数据集类型
 */
public class FSDataset implements FSConstants, FSDatasetInterface {

	/**
	 * Datanode的${dfs.data.dir}属性可以配置多个目录, 每个目录对应一个FSVolume
	 */
	class FSVolume {
		// 用于目录树结构的数据目录
		FSDir dataDir;
		// current目录, 存放Block和校验文件
		File currentDir;
		// 正在写入的文件的目录, 用于节点间复制的写
		File tmpDir;
		// 正在写入的文件的目录, 用于客户端的写
		File blocksBeingWritten; // clients write here
		// 配合数据节点升级使用的临时目录
		File detachDir; // copy on write for blocks in snapshot
	}

	/**
	 * FSVolume的集合
	 */
	static class FSVolumeSet {
		FSVolume[] volumes = null;
		int curVolume = 0;
	}

	/**
	 * FSVolume中的数据目录
	 */
	class FSDir {
		File dir;
		int numBlocks = 0;
		FSDir children[];
		int lastChildIdx = 0;
	}

	static class ActiveFile {
		File file;
		List<Thread> threads = new ArrayList<Thread>(2);
	}

	FSVolumeSet volumes;
	// 保存正在进行写操作的数据块与对应文件的映射
	HashMap<Block, ActiveFile> ongoingCreates = new HashMap<Block, ActiveFile>();
	// 保存已经提交的数据块与对应文件的映射
	HashMap<Block, DatanodeBlockInfo> volumeMap = new HashMap<Block, DatanodeBlockInfo>();;
	
	public FSDataset(DataStorage storage, Configuration conf) throws IOException {
		
	}

	@Override
	public void checkDataDir() throws DiskErrorException {
		

	}

	@Override
	public void finalizeBlock(Block b) throws IOException {
		

	}

	@Override
	public void finalizeBlockIfNeeded(Block b) throws IOException {
		

	}

	@Override
	public InputStream getBlockInputStream(Block b) throws IOException {
		
		return null;
	}

	@Override
	public InputStream getBlockInputStream(Block b, long seekOffset)
			throws IOException {
		
		return null;
	}

	@Override
	public BlockLocalPathInfo getBlockLocalPathInfo(Block b) throws IOException {
		
		return null;
	}

	@Override
	public Block[] getBlockReport() {
		
		return null;
	}

	@Override
	public Block[] getBlocksBeingWrittenReport() {
		
		return null;
	}

	@Override
	public long getChannelPosition(Block b, BlockWriteStreams stream)
			throws IOException {
		
		return 0;
	}

	@Override
	public long getLength(Block b) throws IOException {
		
		return 0;
	}

	@Override
	public MetaDataInputStream getMetaDataInputStream(Block b)
			throws IOException {
		
		return null;
	}

	@Override
	public long getMetaDataLength(Block b) throws IOException {
		
		return 0;
	}

	@Override
	public Block getStoredBlock(long blkid) throws IOException {
		
		return null;
	}

	@Override
	public BlockInputStreams getTmpInputStreams(Block b, long blkoff, long ckoff)
			throws IOException {
		
		return null;
	}

	@Override
	public long getVisibleLength(Block b) throws IOException {
		
		return 0;
	}

	@Override
	public boolean hasEnoughResource() {
		
		return false;
	}

	@Override
	public void invalidate(Block[] invalidBlks) throws IOException {
		

	}

	@Override
	public boolean isValidBlock(Block b) {
		
		return false;
	}

	@Override
	public boolean metaFileExists(Block b) throws IOException {
		
		return false;
	}

	@Override
	public void setChannelPosition(Block b, BlockWriteStreams stream,
			long dataOffset, long ckOffset) throws IOException {
		

	}

	@Override
	public void setVisibleLength(Block b, long length) throws IOException {
		

	}

	@Override
	public void shutdown() {
		

	}

	@Override
	public BlockRecoveryInfo startBlockRecovery(long blockId)
			throws IOException {
		
		return null;
	}

	@Override
	public void unfinalizeBlock(Block b) throws IOException {
		

	}

	@Override
	public void updateBlock(Block oldblock, Block newblock) throws IOException {
		

	}

	@Override
	public void validateBlockMetadata(Block b) throws IOException {
		

	}

	@Override
	public BlockWriteStreams writeToBlock(Block b, boolean isRecovery,
			boolean isReplicationRequest) throws IOException {
		
		return null;
	}

	@Override
	public long getCapacity() throws IOException {
		
		return 0;
	}

	@Override
	public long getDfsUsed() throws IOException {
		
		return 0;
	}

	@Override
	public long getRemaining() throws IOException {
		
		return 0;
	}

	@Override
	public String getStorageInfo() {
		
		return null;
	}

}

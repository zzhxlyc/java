package h.hdfs.protocol;

import java.io.IOException;

import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.DirectoryListing;
import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;
import org.apache.hadoop.hdfs.protocol.LocatedBlock;
import org.apache.hadoop.hdfs.protocol.LocatedBlocks;
import org.apache.hadoop.hdfs.protocol.FSConstants.DatanodeReportType;
import org.apache.hadoop.hdfs.protocol.FSConstants.SafeModeAction;
import org.apache.hadoop.hdfs.protocol.FSConstants.UpgradeAction;
import org.apache.hadoop.hdfs.security.token.delegation.DelegationTokenIdentifier;
import org.apache.hadoop.hdfs.server.common.UpgradeStatusReport;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.hadoop.security.token.Token;

/**
 * 客户端与NameNode通信的IPC接口, 共34个方法
 * fsync(), 只保证文件元数据的修改被保存, 而不保证文件的数据块信息
 * renewLease()和recoverLease()为租约方法, 保持和NameNode通信的心跳, 防止错误产生
 */
public interface ClientProtocol extends VersionedProtocol {

	
	public void abandonBlock(Block b, String src, String holder)
			throws IOException;

	
	public LocatedBlock addBlock(String src, String clientName)
			throws IOException;

	
	public LocatedBlock addBlock(String src, String clientName,
			DatanodeInfo[] excludedNodes) throws IOException;

	
	public LocatedBlock append(String src, String clientName)
			throws IOException;

	
	public void cancelDelegationToken(Token<DelegationTokenIdentifier> token)
			throws IOException;

	
	public boolean complete(String src, String clientName) throws IOException;

	
	public void create(String src, FsPermission masked, String clientName,
			boolean overwrite, short replication, long blockSize)
			throws IOException;

	
	public void create(String src, FsPermission masked, String clientName,
			boolean overwrite, boolean createParent, short replication,
			long blockSize);

	
	public boolean delete(String src) throws IOException;

	
	public boolean delete(String src, boolean recursive) throws IOException;

	
	public UpgradeStatusReport distributedUpgradeProgress(UpgradeAction action)
			throws IOException;

	
	public void finalizeUpgrade() throws IOException;

	
	public void fsync(String src, String client) throws IOException;

	
	public LocatedBlocks getBlockLocations(String src, long offset, long length)
			throws IOException;

	
	public ContentSummary getContentSummary(String path) throws IOException;

	
	public DatanodeInfo[] getDatanodeReport(DatanodeReportType type)
			throws IOException ;

	
	public Token<DelegationTokenIdentifier> getDelegationToken(Text renewer)
			throws IOException;

	
	public HdfsFileStatus getFileInfo(String src) throws IOException;

	
	public DirectoryListing getListing(String src, byte[] startAfter)
			throws IOException ;

	
	public long getPreferredBlockSize(String filename) throws IOException;

	
	public long[] getStats() throws IOException;

	
	public void metaSave(String filename) throws IOException;

	
	public boolean mkdirs(String src, FsPermission masked) throws IOException;

	
	public boolean recoverLease(String src, String clientName)
			throws IOException;

	
	public void refreshNodes() throws IOException;

	
	public boolean rename(String src, String dst) throws IOException;

	
	public long renewDelegationToken(Token<DelegationTokenIdentifier> token)
			throws IOException ;

	
	public void renewLease(String clientName) throws IOException ;

	
	public void reportBadBlocks(LocatedBlock[] blocks) throws IOException ;

	
	public void saveNamespace() throws IOException ;
	
	public void setBalancerBandwidth(long bandwidth) throws IOException ;
	
	public void setOwner(String src, String username, String groupname)
			throws IOException ;
	
	public void setPermission(String src, FsPermission permission)
			throws IOException ;
	
	public void setQuota(String path, long namespaceQuota, long diskspaceQuota)
			throws IOException ;
	
	public boolean setReplication(String src, short replication)
			throws IOException ;
	
	public boolean setSafeMode(SafeModeAction action) throws IOException ;
	
	public void setTimes(String src, long mtime, long atime) throws IOException ;
	
	public long getProtocolVersion(String protocol, long clientVersion) throws IOException ;
	
}
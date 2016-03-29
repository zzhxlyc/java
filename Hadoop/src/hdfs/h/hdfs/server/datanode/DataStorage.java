package h.hdfs.server.datanode;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.hdfs.server.common.Storage;
import org.apache.hadoop.hdfs.server.common.HdfsConstants.NodeType;

/**
 * 存储空间的状态管理
 */
public class DataStorage extends Storage {

	final static String BLOCK_SUBDIR_PREFIX = "subdir";
	final static String BLOCK_FILE_PREFIX = "blk_";
	final static String COPY_FILE_PREFIX = "dncp_";

	String storageID;

	DataStorage() {
		super(NodeType.DATA_NODE);
		storageID = "";
	}

	@Override
	protected void corruptPreUpgradeStorage(File rootDir) throws IOException {

	}

	@Override
	public boolean isConversionNeeded(StorageDirectory sd) throws IOException {
		return false;
	}

}

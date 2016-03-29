package h.hdfs.protocol;

import org.apache.hadoop.hdfs.protocol.DatanodeID;
import org.apache.hadoop.net.NetworkTopology;
import org.apache.hadoop.net.Node;

/**
 * Datanode的状态表示, 还可以通过控制adminState来调整Datanode的状态
 */
public class DatanodeInfo extends DatanodeID implements Node {
	//容量
	protected long capacity;
	//已经使用容量
	protected long dfsUsed;
	//剩余容量
	protected long remaining;
	//最后更新时间
	protected long lastUpdate;
	//流接口服务线程数
	protected int xceiverCount;
	//数据节点在集群中的位置
	protected String location = NetworkTopology.DEFAULT_RACK;
	
	//数据节点的节点状态
	public enum AdminStates {NORMAL, DECOMMISSION_INPROGRESS, DECOMMISSIONED; }
	protected AdminStates adminState;
	
	/*
	 * 返回字符串形式的详细数据报告
	 */
	public String getDatanodeReport() {return null;}
	
	/*
	 * 字符串形式的简易数据报告
	 */
	public String dumpDatanode() {return null;}
	
	public int getLevel() {return 0;}
	public String getNetworkLocation() {return null;}
	public Node getParent() {return null;}
	public void setLevel(int i) {}
	public void setNetworkLocation(String location) {}
	public void setParent(Node parent) {}

}

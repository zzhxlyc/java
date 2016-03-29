package vm.memory;


public class MemoryTest {
	
	static final int _1MB = 1024 * 1024;

	public static void main(String[] args) {
		startMinorGC();
//		newBigObject();
	}
	
	/*
	 * -Xms20M -Xmx20M -Xmn10M  新生代=Eden + one Survivor=9M 内存
	 */
	@SuppressWarnings("unused")
	public static void startMinorGC(){
		byte[] a1 = new byte[2 * _1MB];	//放入Eden, Eden=2M
		byte[] a2 = new byte[2 * _1MB];	//放入Eden, Eden=4M
		byte[] a3 = new byte[2 * _1MB];	//放入Eden, Eden=6M
		byte[] a4 = new byte[4 * _1MB];	//无法放入Eden, 也无法放入Survivor, 触发Minor GC
										//Eden=4M, 老年代=6M
	}
	/*
	 * 	def new generation   total 9216K, used 4396K
	 * 		eden space 8192K,  52% used
	 * 		from space 1024K,  13% used
	 * 		to   space 1024K,   0% used
	 * 	tenured generation   total 10240K, used 6144K
	 */
	
	
	
	
	
	/*
	 * -verbose:gc -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M
		-XX:PretenureSizeThreshold=3145728
	 */
	@SuppressWarnings("unused")
	public static void newBigObject(){
		byte[] a5 = new byte[4 * _1MB];	//直接分配进入老年代内存
	}
	/*
	 * 	def new generation   total 9216K, used 491K
	 * 	tenured generation   total 10240K, used 4096K	
	 */

}

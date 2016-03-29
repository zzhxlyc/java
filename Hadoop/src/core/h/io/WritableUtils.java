package h.io;

import java.io.DataInput;
import java.io.DataOutput;

public class WritableUtils {
	
	public static int getVIntSize(long i) {return 0;}
	
	/**
	 * 写VInt和写VLong是一样的
	 */
	public static int readVInt(DataInput stream) {return 0;}
	public static void writeVInt(DataOutput stream, int i){}
	
	/**
	 * -112到127, 直接写入byte, 只需1个字节
	 * 128->Long.MAX_VALUE, 设有效字节数为n, 第一个字节为-(n+112), 后续为有效字节
	 * -Long.MAX_VALUE->-113, 第一个字节为-(n+120), 后续为有效字节
	 * 
	 * 写负数(小于-112), 由于负数byte有效位太多, 存储其补码
	 * 
	 * 取-112为分界点的原因：-128 + 8 + 8 = -112 （8位表示负数, 8位表示正数）
	 */
	public static int readVLong(DataInput stream) {return 0;}
	public static void writeVLongt(DataOutput stream, long i){}

}

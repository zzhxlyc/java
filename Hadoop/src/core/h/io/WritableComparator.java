package h.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * 可对二进制流对象进行比较 实现接口Comparator, RawComparator. 
 * compare的对象必须为WritableComparable的
 * 
 * 使用方法：WritableComparator c = WritableComparator.get(MyClass extends Writable);
 * c.compare(byte[], int, int, byte[], int, int) c.readInt(byte[], int)
 * 
 */
@SuppressWarnings("unchecked")
public class WritableComparator implements RawComparator {

	/** ************************ 静态区 ****************************** */

	/*
	 * 全局缓存class类->WritableComparator的HashMap，高效构造WritableComparator
	 */
	private static Map<Class<?>, WritableComparator> comparators 
		= new HashMap<Class<?>, WritableComparator>();

	/*
	 * 使用此静态方法获取WritableComparator, 若缓存comparators中没有则new出一个并添加到Map中
	 */
	public static synchronized WritableComparator get(
			Class<? extends WritableComparable> c) {
		return null;
	}

	// 主动注册WritableComparator
	public static synchronized void define(Class c,
			WritableComparator comparator) {
		comparators.put(c, comparator);
	}

	/** ************************ 实例区 ****************************** */

	final Class<? extends WritableComparable> keyClass;
	// 由keyClass.newInstance()出来的实例1, 用来从IO流中readFields()
	private final WritableComparable key1;
	// 由keyClass.newInstance()出来的实例2, 用来从IO流中readFields()
	private final WritableComparable key2;
	// 包装了一层ByteArrayInputStream的DataInputStream, 用来从二进制流中读取Data
	private final DataInputBuffer buffer;

	protected WritableComparator(Class<? extends WritableComparable> keyClass) {
		this.keyClass = keyClass;
		key1 = ReflectionUtils.newInstance(keyClass, null);
		key2 = ReflectionUtils.newInstance(keyClass, null);
		buffer = new DataInputBuffer();
	}

	/*
	 */
	@Override
	public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
		try {
			// parse key1
			buffer.reset(b1, s1, l1); // 重置buffer
			key1.readFields(buffer);

			// parse key1
			buffer.reset(b2, s2, l2);
			key2.readFields(buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return key1.compareTo(key2);
	}

	@Override
	public int compare(Object o1, Object o2) {
		return ((WritableComparable) o1).compareTo((WritableComparable) o2);
	}

	/** *********************** Util方法区 ****************************** */

	public static int compareBytes(byte[] b1, int s1, int l1, byte[] b2,
			int s2, int l2) {
		int end1 = s1 + l1;
		int end2 = s2 + l2;
		for (int i = s1, j = s2; i < end1 && j < end2; i++, j++) {
			int a = (b1[i] & 0xff);
			int b = (b2[j] & 0xff);
			if (a != b) {
				return a - b;
			}
		}
		return l1 - l2;
	}

	public static int hashBytes(byte[] bytes, int length) {
		int hash = 1;
		for (int i = 0; i < length; i++) {
			hash = (31 * hash) + (int) bytes[i];
		}
		return hash;
	}

	public static int readInt(byte[] bytes, int start) {
		return (((bytes[start] & 0xff) << 24)
				+ ((bytes[start + 1] & 0xff) << 16)
				+ ((bytes[start + 2] & 0xff) << 8) + ((bytes[start + 3] & 0xff)));
	}

	public static int readVInt(byte[] bytes, int start) {
		return 0;
	}

}

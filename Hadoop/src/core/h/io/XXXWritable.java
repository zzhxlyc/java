package h.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 提供XXX(Java基础、VInt、VLong等)类型的封装, 可以设置/读取值, 可以写/读序列化 每个类内嵌一个Comparator extends
 * WritableComparator提供此类型的比较, 并
 * 自动调用WritableComparator.define()添加到WritableComparator的全局缓存中
 * 
 * ObjectWritable类, 序列化读写方法，判断顺序：Null->(写入类名)->Array->String->
 * 	Primitive(Boolean、Character、Byte、Short、Integer等)->Enum->指定的Class
 * 每种类型都有自己的序列化方式，最后指定Class的方式就是调用instance.write(DataOutput)
 */
public class XXXWritable {

	private int value;

	public XXXWritable() {
	}

	public XXXWritable(int value) {
		set(value);
	}

	/** Set the value of this IntWritable. */
	public void set(int value) {
		this.value = value;
	}

	/** Return the value of this IntWritable. */
	public int get() {
		return value;
	}

	public void readFields(DataInput in) throws IOException {
		value = in.readInt();
	}

	public void write(DataOutput out) throws IOException {
		out.writeInt(value);
	}

	public static class Comparator extends WritableComparator {
		public Comparator() {
			super(IntWritable.class);
		}

		public int compare(byte[] b1, int s1, int l1, 
				byte[] b2, int s2, int l2) {
			int thisValue = readInt(b1, s1);
			int thatValue = readInt(b2, s2);
			return (thisValue < thatValue ? -1 : (thisValue == thatValue ? 0
					: 1));
		}
	}

	static { // register this comparator
		WritableComparator.define(IntWritable.class, new Comparator());
	}

}

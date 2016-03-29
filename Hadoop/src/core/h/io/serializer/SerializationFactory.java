package h.io.serializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.serializer.Deserializer;
import org.apache.hadoop.io.serializer.Serialization;
import org.apache.hadoop.io.serializer.Serializer;

/**
 * 默认有2种实现：
 * WritableSerialization(支持Writable的序列化):
 * 		就是调用((Writable)instance).write(...)
 * JavaSerialization(支持Serializable的序列化)
 *		就是调用ObjectOutputStream.writeObject((Serializable)instance)
 */
@SuppressWarnings("unused")
public class SerializationFactory extends Configured {

	private List<Serialization<?>> serializations = 
		new ArrayList<Serialization<?>>();

	/**
	 * 读取配置文件项io.serializations, 然后根据里面设置的序列化实现类, 都实例化出来缓存着
	 * 配置文件中的顺序影响优先选择的序列化机制
	 */
	public SerializationFactory(Configuration conf) {
		super(conf);
		String clazz = "org.apache.hadoop.io.serializer.WritableSerialization";
		for (String serializerName : conf.getStrings("io.serializations",
				new String[] { clazz })) {
			// serializations.add(serializerName.newInstance());
		}
	}

	public <T> Serializer<T> getSerializer(Class<T> c) {
		return getSerialization(c).getSerializer(c);
	}

	public <T> Deserializer<T> getDeserializer(Class<T> c) {
		return getSerialization(c).getDeserializer(c);
	}

	/**
	 * serialization.accept(c)的判断：
	 * 	WritableSerialization：若c可以表示为Writable, 则接受
	 * 	JavaSerialization：若c可以表示为Serializable, 则接受
	 */
	@SuppressWarnings("unchecked")
	public <T> Serialization<T> getSerialization(Class<T> c) {
		for (Serialization serialization : serializations) {
			if (serialization.accept(c)) {
				return (Serialization<T>) serialization;
			}
		}
		return null;
	}

}

package h.io.compress;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * 用来获取压缩Codec类, 读取io.compression.codecs属性值, 
 * 	若属性值为空默认采用 DefaultCodec和GzipCodec
 */
public class CompressionCodecFactory {

	private SortedMap<String, CompressionCodec> codecs = null;

	/**
	 * 在codecs这TreeMap中保存的方式< 后缀名倒序, 压缩Codec类 >
	 * 这样到时候的文件名直接倒序就可以用startWith()方法来匹配
	 */
	private void addCodec(CompressionCodec codec) {
		String suffix = codec.getDefaultExtension();
		codecs.put(new StringBuffer(suffix).reverse().toString(), codec);
	}

	public CompressionCodecFactory(Configuration conf) {
		codecs = new TreeMap<String, CompressionCodec>();
		List<Class<? extends CompressionCodec>> codecClasses = 
			new ArrayList<Class<? extends CompressionCodec>>();
		if (codecClasses == null) {
			addCodec(new GzipCodec());
			addCodec(new DefaultCodec());
		} 
		else {
			Iterator<Class<? extends CompressionCodec>> itr = codecClasses
					.iterator();
			while (itr.hasNext()) {
				CompressionCodec codec = ReflectionUtils.newInstance(
						itr.next(), conf);
				addCodec(codec);
			}
		}
	}

}

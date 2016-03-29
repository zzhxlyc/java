package h.fs;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * Hadoop使用的文件系统的抽象类, 用户创建和缓存具体文件系统实现类, 以及提供各种静态方法
 */
public abstract class FileSystem {

	/**
	 * 必须三个属性都一致, Key才算相等
	 */
	static class Key {
		final String scheme;
		final String authority;
		final UserGroupInformation ugi;

		Key(URI uri, Configuration conf) throws IOException {
			scheme = uri.getScheme() == null ? "" : uri.getScheme()
					.toLowerCase();
			authority = uri.getAuthority() == null ? "" : uri.getAuthority()
					.toLowerCase();
			this.ugi = UserGroupInformation.getCurrentUser();
		}
	}

	static class Cache {
		private Map<Key, FileSystem> map = new HashMap<Key, FileSystem>();

		FileSystem get(URI uri, Configuration conf) throws IOException {
			Key key = new Key(uri, conf);
			FileSystem fs = null;
			synchronized (this) {
				fs = map.get(key);
			}
			if (fs != null) {
				return fs;
			}
			fs = createFileSystem(uri, conf);
			map.put(key, fs);
			return fs;
		}
	}

	private static FileSystem createFileSystem(URI uri, Configuration conf)
			throws IOException {
		Class<?> clazz = conf.getClass("fs." + uri.getScheme() + ".impl", null);
		FileSystem fs = (FileSystem) ReflectionUtils.newInstance(clazz, conf);
		//fs.initialize(uri, conf);
		return fs;
	}

}

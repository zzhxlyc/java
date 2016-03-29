package h.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 配置类, 如果用new Configuration(), 默认只读取default的2个xml(会重新解析)
 *	新的配置文件需要用addResource()方法手动添加进去
 */
public class Configuration {

	// GC时就被回收; 这里其实当WeakHashSet用, Value都是null, 
	// 存放被new出来的Configuration对象
	private static final WeakHashMap<Configuration, Object> REGISTRY 
		= new WeakHashMap<Configuration, Object>();

	// 使用CopyOnWriteArrayList为了多线程同步，默认配置文件全局共享
	private static final CopyOnWriteArrayList<String> defaultResources = new CopyOnWriteArrayList<String>();

	// debug or not
	private boolean quietmode = true;

	// 添加配置文件的队列, 存放文件名、URL类、Path类(hadoop的)、inputstream类等
	private ArrayList<Object> resources = new ArrayList<Object>();

	// xml文件的property中<final>true</final>的项, 不能被子配置文件修改
	private Set<String> finalParameters = new HashSet<String>();

	private boolean loadDefaults = true;

	// 当storeResource = true时, updatingResource才会存在
	private boolean storeResource;
	private HashMap<String, String> updatingResource;

	private Properties properties;

	static {
		addDefaultResource("core-default.xml");
		addDefaultResource("core-site.xml");
	}

	public static synchronized void addDefaultResource(String name) {
		if (!defaultResources.contains(name)) {
			defaultResources.add(name);
			for (Configuration conf : REGISTRY.keySet()) {
				if (conf.loadDefaults) {
					conf.properties = null;
					conf.finalParameters.clear();
				}
			}
		}
	}

	// 在第一次试图去读取属性值时候作解析被调用
	@SuppressWarnings("unused")
	private void loadResources() {
		boolean quiet = quietmode;
		properties = new Properties();
		if (loadDefaults) {
			for (String resource : defaultResources) {
				loadResource(properties, resource, quiet);
			}
			// support the hadoop-site.xml as a deprecated case
			if (getClass().getClassLoader().getResource("hadoop-site.xml") != null) {
				loadResource(properties, "hadoop-site.xml", quiet);
			}
		}

		for (Object resource : resources) {
			loadResource(properties, resource, quiet);
		}
	}

	// 用javax.xml.parsers.DocumentBuilder进行XML解析
	private void loadResource(Properties properties, Object name, boolean quiet) {
		// 遍历整个xml的propeerty属性
		for (int i = 0; i < 0; i++) {
			String attr = null, value = null;
			boolean finalParameter = false; // 解析<final></final>中的值
			properties.setProperty(attr, value);
			if (storeResource) {
				updatingResource.put(attr, name.toString());
			}
			if (finalParameter) {
				finalParameters.add(attr);
			}
		}
	}

	// 读取属性值, 执行代入$(...)
	public String get(String name) {
		return substituteVars(properties.getProperty(name));
	}

	// 读取原值, 不执行代入
	public String getRaw(String name) {
		return properties.getProperty(name);
	}

	// 代入配置文件中 $(...) 的值, 整个配置文件解析完才代入，所以先后顺序无关
	// 支持嵌套代入, 但最多代入20次，否则抛异常
	// 支持系统属性和Java命令行属性值的代入, 比如$(user.home)
	private String substituteVars(String expr) {
		return null;
	}

}

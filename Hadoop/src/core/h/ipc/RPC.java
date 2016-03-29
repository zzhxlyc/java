package h.ipc;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.net.SocketFactory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.ipc.VersionedProtocol;

/**
 * 管理RPC中的Client和Server的类, 起到一个门面的作用
 */
public class RPC {

	/**
	 * 对RPC调用函数的封装, 函数名, 参数类型, 参数实例 
	 */
	static class Invocation implements Writable {
		String methodName;
		Class<?>[] parameterClasses;
		Object[] parameters;

		public Invocation(Method method, Object[] parameters) {
			this.methodName = method.getName();
			this.parameterClasses = method.getParameterTypes();
			this.parameters = parameters;
		}

		public void readFields(DataInput in) throws IOException {
		}

		public void write(DataOutput out) throws IOException {
		}
	}

	/**
	 * 对Client进行缓存, 一个SocketFactory对应一个Client端
	 */
	static class ClientCache {
		Map<SocketFactory, Client> clients = new HashMap<SocketFactory, Client>();
	}
	static ClientCache CLIENTS = new ClientCache();

	/**
	 * 代理类的函数调用实现
	 */
	static class Invoker implements InvocationHandler {
		private Client.ConnectionId remoteId;
		private Client client;

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			ObjectWritable value = (ObjectWritable) client.call(new Invocation(
					method, args), remoteId);
			return value;
		}
	}

	/**
	 * 对抽象类ipc.Server的具体实现
	 */
	public static class Server extends org.apache.hadoop.ipc.Server {
		private Object instance;

		public Server(Object instance, Configuration conf, String bindAddress,
				int port, int numHandlers) throws IOException {
			super(bindAddress, port, Invocation.class, numHandlers, conf, null,
					null);
			this.instance = instance;
		}

		@Override
		public Writable call(Class<?> protocol, Writable param, long receiveTime)
				throws IOException {
			try {
				Invocation invocation = (Invocation) param;
				Method method = protocol.getMethod(invocation.methodName,
						invocation.parameterClasses);
				method.setAccessible(true);
				Object value = method.invoke(instance, invocation.parameters);
				return new ObjectWritable(method.getReturnType(), value);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/************************** Method Section *****************************/

	public static VersionedProtocol getProxy() {
		// VersionedProtocol proxy = Proxy.newProxyInstance(..., ..., new
		// Invoker());
		return null;
	}

	public static Server getServer(final Object instance,
			final String bindAddress, final int port, final int numHandlers)
			throws IOException {
		return new Server(instance, null, bindAddress, port, numHandlers);
	}

}

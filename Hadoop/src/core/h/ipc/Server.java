package h.ipc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * RPC服务器端, NIO处理TCP; 和Tomcat的模式比较像
 * 
 * Listener和Reader
 * 	Listener构造, 起了N个Reader, reader线程由线程池启动, 运行在run()中, 由于reader中的selector
 * 		还没有绑定任何channel和事件, 所有run()中的select()无限阻塞
 * 	Listener的doAccept(), 得到某个空闲的reader()线程, 调用reader.stardAdd()
 * 		stardAdd(), 设置reader.adding = true, selector.wakeup()
 * 		reader在run()中陷入while(adding){wait();}循环
 * 	doAccept()继续, 完成reader的selector的事件注册和Connection的新建, 调用reader.finishAdd()
 * 		finishAdd(), reader.adding = false, reader.notify()
 * 		reader在run()跳出wait循环, 略过第一次无意义大循环后, 继续进入select()等待事件, 进入状态
 * 	reader.selector并不解除绑定, 下一次被doAccept()绑定新的channel
 * 	
 * 	1):由于Reader没有使用/回收机制, 只是按顺序轮, 如果仍在select()的reader又被doAccept()怎么办？
 * 	2):Reader线程跑在newFixedThreadPool(Reader_Num)中, 这个有何意义？
 */
public abstract class Server {

	private Class<? extends Writable> paramClass;
	private Configuration conf;
	volatile private boolean running = true;
	// Call就好比生产者-消费者队列, 由Reader生产, 由Handler消费
	private BlockingQueue<Call> callQueue;

	// 全局一个responder, 一个线程
	private Responder responder = null;
	// 全局一个listener, 一个线程, 但带有多个可设置的Reader
	private Listener listener = null;
	// 用参数设置的多个Handler, 多个线程, 用的JDK线程池框架
	private int handlerCount;
	private Handler[] handlers = null;
	
	/**
	 * 包装结果类和Connection的封装类
	 */
	private static class Call {
		int id; // the client's call id
		private Writable param; // the parameter passed
		private Connection connection;
		private long timestamp;

		public Call(int id, Writable param, Connection connection) {}
	}

	/**
	 * 监听类, 发散Socket给Reader
	 */
	private class Listener extends Thread {
		private ServerSocketChannel acceptChannel;
		private Selector selector;
		private ExecutorService readPool;
		private int readThreads;
		private Reader[] readers = null;

		public Listener() throws IOException {
			readers = new Reader[readThreads];
			readPool = Executors.newFixedThreadPool(readThreads);
			for(Reader reader : readers){
				readPool.execute(reader);
			}
			acceptChannel.register(selector, SelectionKey.OP_ACCEPT);
		}

		@Override
		public void run() {
			while (running) {
				SelectionKey key = null;
				try {
					doAccept(key);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		/*
		 * Listener线程, 接到Socket就调用此函数, 新建连接并交给Reader处理
		 */
		void doAccept(SelectionKey key) throws IOException, OutOfMemoryError {
			ServerSocketChannel server = (ServerSocketChannel) key.channel();
			SocketChannel channel;
			while ((channel = server.accept()) != null) {
				Reader reader = getReader();
				SelectionKey readKey = reader.registerChannel(channel);
				Connection c = new Connection();
				readKey.attach(c);
			}
		}

		/*
		 * Reader线程, 处理读
		 */
		void doRead(SelectionKey key) throws InterruptedException {
			Connection c = (Connection) key.attachment();
			c.readAndProcess();
		}

		/**
		 * Listener内部的静态类, 用于处理读请求
		 */
		private class Reader implements Runnable {
			private Selector readSelector = null;

			@Override
			public void run() {
				try {
					synchronized (this) {
						while (running) {
							SelectionKey key = null;
							doRead(key);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			public synchronized SelectionKey registerChannel(
					SocketChannel channel) throws IOException {
				return channel.register(readSelector, SelectionKey.OP_READ);
			}
		}

		private int currentReader = 0;
		Reader getReader() {
			currentReader = (currentReader + 1) % readers.length;
			return readers[currentReader];
		}

	}

	/**
	 * 用来将结果传输回Client, 占据一个线程
	 */
	private class Responder extends Thread {
		void doRespond(Call call) {}
	}

	/**
	 * Call的消费者对象, 处理RPC过程调用, 每个Handler一个线程
	 * 		因为Handle用的线程主要用来处理逻辑/数据库等重量级调用, 所以将网络写回这一块交给单独的线程
	 * 		因为网络交互不稳定可能比较耗时间, 阻塞着不影响处理IPC的请求
	 * 		但是当responseQueue.size() == 1时, 则在Handler线程中写回客户端
	 */
	private class Handler extends Thread {
		@Override
		public void run() {
			ByteArrayOutputStream buf = null;
			while (running) {
				try {
					final Call call = callQueue.take();
					Writable value = call(call.connection.protocol, call.param,
							call.timestamp);
					setupResponse(buf, call, value);
					responder.doRespond(call);
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 连接的抽象
	 * 服务器端写数据时, 数据分帧使用"约定长度", 即一个Writable类自己去控制
	 */
	public class Connection {
		private ByteBuffer data;
		private boolean headerRead = false;
		Class<?> protocol;

		public int readAndProcess() {
			processOneRpc(data.array());
			return 0;
		}
		private void processOneRpc(byte[] buf){
			if (headerRead) {
				processData(buf);
			} 
			else {
				processHeader(buf);
				headerRead = true;
			}
		}
		private void processHeader(byte[] buf){}
		private void processData(byte[] buf) {
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
					buf));
			try {
				int id = dis.readInt(); // try to read an id
				Writable param = ReflectionUtils.newInstance(paramClass, conf);// read
				param.readFields(dis);
				Call call = new Call(id, param, this);
				callQueue.put(call);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Server.start() 带动所有组件线程start()
	 */
	public synchronized void start() {
		responder.start();
		listener.start();
		handlers = new Handler[handlerCount];
		for (int i = 0; i < handlerCount; i++) {
			handlers[i] = new Handler();
			handlers[i].start();
		}
	}

	/*
	 * 将结果类序列化写入Response
	 */
	private void setupResponse(ByteArrayOutputStream response, Call call,
			Writable rv) {
		try {
			DataOutputStream out = new DataOutputStream(response);
			rv.write(out);
		} catch (IOException e) {}
	}

	/*
	 * 由RPC.Server继承实现
	 */
	public abstract Writable call(Class<?> protocol, Writable param,
			long receiveTime);

}

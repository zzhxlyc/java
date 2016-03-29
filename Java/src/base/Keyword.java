package base;

public class Keyword {
	
	//多线程中，强制每次读取最新在内存中的值，而不是读寄存器的备份，防止编译器进行某些优化(指令重排序优化)
	//仅保证了读一致性, 并不保证写一致性, 所以并发写还是需要加锁; 适合不太被修改的线程共享的状态变量
	//与synchronized的区别是，多个线程可以同时访问这个变量
	volatile int num;
	
	//当被序列化时，transient变量被忽略
	transient int nouse;
	
	//final只有在编译期有效, runtime期可以被反射修改
	final int cannotChange = 1;
	
	Object lock = new Object();
	
	//native 调用其他语言库（C），没有函数body（类似abstract）；和JNI(Java Native Interface)一起工作
	native void method();
	
	//strictfp保证浮点运算的规范通用性
	strictfp double doubleValue(){
		return 0.0;
	}
	
	//synchronized方法，若为实体方法加锁对象为实例对象，若为静态方法加锁对象为class对象
	public synchronized void method(int n) {}
	
	//synchronized块
	public void method(int n, int nn) {
		//加锁类实例，本类; 锁住了本类实例，本类中所有synchronized方法都会被阻塞
		synchronized(this){}
		
		//加锁类实例，field; 只锁住需要此lock的方法
		synchronized(lock){}
		
		//不能对primitive进行加锁，必须为对象
//		synchronized(n){}
		
		//加锁类的class对象
		synchronized(Object.class){}
	}

}

@interface Annotation{
	String value() default "xxx";	//传给annotion的参数，可传参可用默认; default还用在switch中
}

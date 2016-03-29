package init;

/*
 * 静态初始化，表现为加载Class类
 * 触发类加载的情形（有且仅有）：
 * 	1.遇到new，getstatic，putstatic，invokestatic指令
 * 	2.使用反射调用类, Class.forName()
 * 	3.初始化一个类时，会先初始化其父类
 * 	4.VM启动时，初始化main函数所在的类
 */
public class LoadClassFile {

	public static void main(String[] args) {
		LoadClassFile test = new LoadClassFile();
//		test.访问静态常量不触发加载Class();
//		test.加载Class初始化静态域先于构造();
//		test.forName();
//		test.Class点class();
//		test.newArray不触发加载Class();
		test.通过子类访问父类静态字段不加载子类Class();
	}
	
	//---------------触发初始化-----------------
	
	// 加载class类会触发static域的初始化，所以可以通过static域信息来查看类被加载的时机
	public void 加载Class初始化静态域先于构造(){
		//先载入class类，初始化静态域，再进入构造
		new A();
	}
	
	//Class.forName触发类的初始化，static域
	public void forName(){
		System.out.println("---------------forName begin---------------");
		try {
			System.out.println(A.aa);		//访问static final成员不触发加载
			Class.forName("loadClass.A");	//加载class，打印出static block语句
			System.out.println(A.bb);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//---------------不触发初始化-----------------
	
	//A.class不触发类的初始化
	public void Class点class(){
		Class<A> clazz = A.class;
		System.out.println(clazz.getName());
	}
	
	//既然都拿到了obj了，早有代码触发加载了...
	public void obj点getClass(){
		A a = new A();
		a.getClass();
	}
	
	public void 访问静态常量不触发加载Class(){
		//访问public static final的常量，不会调用任何static初始化的
		//final常量直接放常量池，没有与A类绑定，所以并没有载入A类的class对象
		System.out.println(A.aa + "\n");
		
		//访问public static（不是final对象），需要载入class对象并初始化static域
		System.out.println(A.bb);
	}
	
	//编译器自动生成了一个这个类的数组的类(继承自Object)
	public void newArray不触发加载Class(){
		A[] list = new A[5];
		System.out.println(list.getClass().getName());		// [Linit.A;
	}
	
	public void 通过子类访问父类静态字段不加载子类Class(){
		System.out.println(B.bb);
	}

}

class A {
	//static final编译期常量，不需要被初始化就可以被读取
	public static final String aa = "public static final String aa in A";
	
	//不是final的，就需要被初始化后才能访问
	public static String bb = "public static String bb in A";
	
	static {
		System.out.println("It is A's static block in A");
	}
	
	A(){
		System.out.println("It is A's construction in A");
	}
}

class B extends A{
	static {
		System.out.println("It is B's static block in B");
	}
	B(){
		System.out.println("It is B's construction in B");
	}
}

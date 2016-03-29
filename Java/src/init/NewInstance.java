package init;

/*
 * 动态初始化, new一个变量
 * 先判断是否已加载Class->静态初始化
 * 然后再动态初始化
 */
public class NewInstance {
	//初始化步骤1：静态变量和静态块, 属于LoadClass的范畴, 只执行一次
	private static String a = NewInstance.a + "->static field init->";
	static{
		System.out.println("only once, static block init, begin");
		a += "static block->";
		System.out.println("only once, static block init, end");
	}
	
	//初始化步骤2：field和普通块, 属于NewInstance的范畴, 每次重新执行
	private String b = this.b + "->field init->";
	{
		System.out.println("every time, this is block");
	}
	
	//初始化步骤3：构造, 属于NewInstance的范畴, 每次重新执行
	public NewInstance(){
		a += "我在构造函数里被改了->";
		b += "我在构造函数里被改了->";
	}
	
	public void print(){
		System.out.println("a = " + a);
		System.out.println("b = " + b);
	}
	
	public static void main(String[] args) {
		NewInstance instance = new NewInstance();
		instance.print();
		
		NewInstance instance2 = new NewInstance();	//第二次new, 跳过步骤1, 只执行2.3
		instance2.print();
	}

}

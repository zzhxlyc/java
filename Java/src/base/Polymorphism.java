package base;

public class Polymorphism {
	
	/**
	 * 类的静态域和field没有多态性，只有method有多态性
	 */

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Parent obj1 = new Child();
		Child obj2 = new Child();
		
		//注意直接访问a和s，结果是不同的；通过方法调用得到的a和s是相同的，因为method多态了
		System.out.println(obj1.a + "\t" + obj1.s + "\t" + obj1.getA() + "\t" + obj1.getS());
		System.out.println(obj2.a + "\t" + obj2.s + "\t" + obj2.getA() + "\t" + obj2.getS());
	}

}

class Parent {
	
	static String s = "abc";
	
	public int a = 100;
	
	public int getA(){
		return a;
	}
	
	public String getS(){
		return s;
	}
}

class Child extends Parent {
	
	static String s = "xyz";
	
	public int a = 200;
	
	public int getA(){
		return a;
	}
	
	public String getS(){
		return s;
	}
}

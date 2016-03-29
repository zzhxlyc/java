package base;

public class Cover {
	
	/**
	 * 遮掩：当一个变量和一个类型具有相同的名字，并且它们位于相同的作用域时，变量名具有优先权
	 * 优先级：变量名 > 类名 > 包名
	 * 
	 * 遮蔽：
	 * 优先级：局部变量 > field或者static field
	 */
	
	static class Y {
		static String Z = "Black";
	}

	static C Y = new C();

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		System.out.println(Cover.Y.Z);// White
		System.out.println(((Cover.Y) null).Z);// Black
	}
}

class C {
	String Z = "White";
}

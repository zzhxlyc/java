package base;

/**
 * 只要有除数或者被除数之一是double，则不触发异常机制，结果用(-)Infinity or NaN表示
 *
 */
public class DivideZero {
	
	public static void main(String[] args) {
//		intDivideZero();
		doubleDivideZero();
	}
	
	public static void intDivideZero(){
		int a = 5;
		long b = 5L;
		System.out.println(a / 0);
		System.out.println(b / 0);
		//Throw RuntimeException
		System.out.println(a / 0.0);	//Infinity
		System.out.println(b / 0.0);	//Infinity
	}
	
	public static void doubleDivideZero(){
		double a = 5.4;
		float b = 4.2f;
		System.out.println(a / 0);
		System.out.println(-b / 0);
		System.out.println(0.0 / 0);
		//输出Infinity, -Infinity, NaN
		System.out.println(a / 0.0);
		System.out.println(-b / 0.0);
		System.out.println(0.0 / 0.0);
		System.out.println(0 / 0.0);
		//输出Infinity, -Infinity, NaN, NaN
	}
}

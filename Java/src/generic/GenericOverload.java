package generic;

import java.util.ArrayList;

/*
 * 泛型信息可以作为不同的参数类型来实现重载, 但是必须由不同的返回值作为区别才能实现
 */
public class GenericOverload {
	
	//这种重载必须要返回值不同才能实现（因为泛型的类型擦除, 参数列表其实是一样的）
	public static int f(ArrayList<Integer> list){return 0;}
	
	//返回值不能为int, 否则编译不通过（说重复的函数）
	public static String f(ArrayList<String> list){return "1";}
	
	public static void main(String[] args) {
		ArrayList<String> a = new ArrayList<String>();
		System.out.println(f(a));
		ArrayList<Integer> b = new ArrayList<Integer>();
		System.out.println(f(b));
//		ArrayList<?> c = new ArrayList<Integer>();
//		System.out.println(f(c));	编译不通过， 无此参数类型
//		Object o = new ArrayList<Integer>();
//		System.out.println(f(o));	编译不通过， 无此参数类型
	}

}

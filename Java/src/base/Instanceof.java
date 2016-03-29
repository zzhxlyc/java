package base;

import java.util.Date;

public class Instanceof {
	
	/**
	 * instanceof操作符有这样的要求：
	 * 1、左操作数要是一个对象或null，右操作数是类名
	 * 2、这两个操作数的类型是要父子关系（左是右的子类，或右是左的子类都行），否则编译时就会出错
	 */

	public static void main(String[] args) {
		someSpecial();
	}
	
	public static void someSpecial(){
		System.out.println(null instanceof Object);//false
		System.out.println(new Object() instanceof String);//false
		System.out.println(new String() instanceof Object);//true
		System.out.println(new Date().toString() instanceof String);//true
	}
	
	public static void notValid(){
		//完全没有继承实现关系的不能用instanceof来判断, 出现编译错误
		//System.out.println(new Date() instanceof String);	//编译错误
	}

}

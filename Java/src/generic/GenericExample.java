package generic;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class GenericExample {
	
	public static void main(String[] args) {
		addErrorType();
	}
	
	/*
	 * 可以像泛型数组中加入任意Object元素, 只要将其泛型信息丢失掉, 泛型只是个语法糖
	 */
	@SuppressWarnings("unchecked")
	public static void addErrorType(){
		Object o = new ArrayList<String>();		//丢失泛型类型信息
		//ArrayList<Object> list = new ArrayList<String>();		编译错误, 泛型不能向上转型
		ArrayList<Object> list = (ArrayList<Object>)o;
		list.add(new Integer(1));	//ArrayList<String>加入了Integer
		list.add(new Object());
		for(Object obj : list){		//所有元素转化为Object, 不会出错
			System.out.println(obj);
		}
	}
	
	//使用Object去获取泛型参数的Method
	public static void reflect(){
		try {
			Method method = ArrayList.class.getMethod("add", Object.class);
			System.out.println(method);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

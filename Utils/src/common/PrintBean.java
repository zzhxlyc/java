package common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PrintBean {
	
	public static void printByGetMethod(Object obj){
		Class<?> c = obj.getClass();
		Field[] fieldList = c.getDeclaredFields();
		for (Field field : fieldList) {
			System.out.print(field.getName() + "\t");
			String getFunction = "get" + upcaseFirst(field.getName());
			if(field.getGenericType().toString().equals("boolean")){
				getFunction = "is" + upcaseFirst(field.getName());
			}
			Method method = null;
			try {
				method = c.getDeclaredMethod(getFunction, new Class[] {});
			} catch (NoSuchMethodException e) {
				System.err.println(field.getName()
						+ " does not have a matched get method");
			} catch (SecurityException e) {
				System.err.println(field.getName()
						+ "'s get method is not public");
			}
			try {
				if (method != null) {
					Object value = method.invoke(obj, new Object[] {});
					if (value == null) {
						System.err.println("null");
					} else {
						System.out.println(value);
					}
				}
			} catch (IllegalArgumentException e) {
				System.err.println(field.getName()
						+ "'s get method must have no argument");
			} catch (IllegalAccessException e) {
				System.err.println(field.getName()
						+ "'s get method must be public");
			} catch (InvocationTargetException e) {
				System.err.println(e);
			}
		}
	}
	
	private static String upcaseFirst(String s){
		String c = s.charAt(0) + "";
		return c.toUpperCase() + s.substring(1);
	}
}

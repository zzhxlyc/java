package Interface;

/*
 * 接口可以多重继承
 * interface内部所有的Method, static inner Class, 都是默认public的
 */
public interface Inteface {
	
	// method 只允许有{public, abstract}2个修饰符, 默认都是public abstract的
	int getValue();
	abstract int getKey();
	
	// inner interface只允许有{public, static}2个修饰符, 默认都是public static的
	interface SubInteface{}
	static interface SubStaticInteface{}
	
	// inner class只允许有{public, static}2个修饰符, 默认都是public static的
	// 不存在内部类, 全部是静态内部类!
	class SubClass{}
	static class SubStaticClass{}
	
}

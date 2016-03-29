package method;

import java.io.Serializable;

public class Overload {
	
	static class Test {
		static void f(char c){print("char");}
		static void f(short c){print("short");}	//不会转型到short, 因为不是向上转型
		static void f(int c){print("int");}
		static void f(long c){print("long");}
		static void f(float c){print("float");}
		static void f(double c){print("double");}
		static void f(Character c){print("Character");}
		static void f(Integer c){print("Integer");}		//不会转型到Integer
		static void f(Serializable c){print("Serializable");}//Character实现了Serializable接口
		static void f(Object c){print("Object");}
		static void f(char... c){print("char...");}	//变长参数优先级最低
		static void f(int... c){print("int...");}	//当仅存在多个变长参数的重载时会导致编译错误
		static void print(String s){System.out.println(s);}
	}
	
	public static void main(String[] args) {
		// f(1) -> int -> long -> float -> double -> Integer -> Serializable
		//	-> Object -> int...
		Test.f(1);
		// f('a') -> char -> int -> long -> float -> double -> Character -> Serializable
		// -> Object -> char... -> int...（当char...和int...同时存在时，调用发生编译错误无法确定调用函数）
		Test.f('a');
	}

}

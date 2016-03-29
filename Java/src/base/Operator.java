package base;

public class Operator {
	
	/*
	 * 因为||的优先级比&&低
	 * true || 会直接忽略后面的表达式
	 * false && 并不会直接返回仍会继续执行完毕
	 */
	public static void 逻辑操作符的优先级(){
		System.out.println(true || false && false);	//true
		System.out.println(false && true || true);	//true
	}
	
	//复合赋值 E1 op= E2 等价于简单赋值E1 = (T)((E1)op(E2), 其中T是E1的类型
	//复合赋值表达式自动地将它们所执行的计算的结果转型为其左侧变量的类
	public static void 复合赋值表达式(){
		short s1 = 1;
//		s1 = s1 + 1;	error
		s1 += 1;		//ok, 因为s1 + 1已经被默认转化为short
	}
	
	//Java语言规范描述到 ： 操作符的操作数是从左向右求值的
	public static void 长表达式(){
		int x = 1984; // (0x7c0)
		int y = 2001; // (0x7d1)
		x^= y^= x^= y;	// x ^= (**), 提取x的值前于后面表达式的计算
		//x = 1984^(2001^(1984^2001)) = 0;
		System.out.println("x = " + x + "; y = " + y);	//x = 0; y = 1984
	}
	
	/*
	 * 移位数按照操作数的位数来取余
	 * >> 	按照符号位补0或是1
	 * >>>	补0
	 */
	public static void 移位操作符(){
		//对int的移位会对32取余, 对long的移位会对64取余, 没有任何移位长度可以让一个数丢弃所有的位
		System.out.println(-1 << 32);	//-1
		
		System.out.println(-1 >> 1);	//-1
		System.out.println(-1 >>> 1);	//2147483647
		
		//byte short char 移位时先类型转化为int再移位
		System.out.println(((byte)-1) >>> 1);		//2147483647
		System.out.println(((short)-1) >>> 1);		//2147483647
		System.out.println(((char)-1) >>> 1);		//32767
	}
	
	public static void 取余支持double(){
		System.out.println(15.2 % 5);	//0.2
		System.out.println(15 % 5.2);	//4.6
	}
	
	public static void main(String[] args) {
		移位操作符();
	}

}

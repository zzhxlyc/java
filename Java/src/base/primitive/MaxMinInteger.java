package base.primitive;

public class MaxMinInteger {
	
	public static void main(String[] args) {
		minIntegerValue();
		maxIntegerValue();
	}

	/*
	 * int最大负值的运算, 取反得原数且容易造成溢出
	 */
	public static void minIntegerValue(){
		System.out.println("value\t" + Integer.MIN_VALUE);
		//-2147483648  <==> 10000000 00000000 00000000 00000000
		
		System.out.println("negative\t" + (Integer.MIN_VALUE == -Integer.MIN_VALUE));
		//10000000 00 00 00 取反得 0111111 ff ff ff， 加1得原数
		
		System.out.println("sum\t" + (Integer.MIN_VALUE + Integer.MIN_VALUE));	//溢出
		//10000000 00 00 00 * 2 = 1 00 00 00 00 = 0/32位
		
		System.out.println("abs\t" + Math.abs(Integer.MIN_VALUE));
		//Math.abs当参数p < 0，返回-p  于是返回 -Integer.MIN_VALUE => Integer.MIN_VALUE
		//Math.abs()不一定保证返回非负值
	}
	
	public static void maxIntegerValue(){
		System.out.println("value\t" + Integer.MAX_VALUE);
		//2147483647	0111111 ff ff ff
		
		System.out.println("negative\t" + -Integer.MAX_VALUE);
		//-2147483647	1000000 00 00 00000001
		
		System.out.println("sum\t" + (Integer.MAX_VALUE + Integer.MAX_VALUE));	//溢出
		//结果为-2		ff ff ff 11111110
	}
}

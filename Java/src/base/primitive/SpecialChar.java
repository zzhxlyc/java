package base.primitive;

public class SpecialChar {
	
	public static void main(String[] args) {
//		transferOfChar();
//		unicodeOf双引号();
		char相加();
	}
	
	/**
	 * char没有符号位，值为0-65535
	 * 将char扩展为更宽的int或者long，为0扩展（值扩展）
	 */
	public static void transferOfChar(){
		byte b = -2;		// 2'1111 1110
		System.out.println("byte b = " + b);
		char c = (char)b;	// 2'1111 1111 1111 1110, byte to char符号扩展
		System.out.println("byte to char c = " + (c - '\0'));
		short s = (short)c;	// short有符号位，即-2
		System.out.println("char to short s = " + s);
		int i = (int)c;		// 2'0000 0000 0000 0000 1111 1111 1111 1110， char无符号扩展
		System.out.println("char to int i = " + i);
		long l = (long)c;
		System.out.println("char to long l = " + l);
		float f = (float)c;
		System.out.println("char to float f = " + f);
		double d = (double)c;
		System.out.println("char to double d = " + d);
		
		System.out.println((char)2 == 2);  //true
		System.out.println((char)-2 == -2);//false
		System.out.println((char)-2 == 65534);//true
	}
	
	//char提升到int后执行加法
	public static void char相加(){
		System.out.println('a' + 'b');
	}
	
	public static void unicodeOf双引号(){
		//结果都为2
		System.out.println("a\u0022.length() + \u0022b".length());
		System.out.println("a".length() + \u0022b".length());
		System.out.println("a".length() + "b".length());
		//Unicode编码表示的字符是在编译期间就转换成了普通字符，\u0022是 "
	}

}

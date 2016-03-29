package base.primitive;

public class NumberTransfer {
	
	public static void main(String[] args) {
//		short_And_byte();
//		intToLong();
		allToChar();
	}
	
	/**
	 * byte/short和常数(int)运算返回int
	 * byte与byte, short与short运算返回int
	 */
	@SuppressWarnings("unused")
	public static void short_And_byte(){
		//short
		short s1 = 1, s2 = 2;	//ok
//		short s3 = s1 + 1;		//error, int to short
		short s3 = (short)(s1 + 1);
//		short s4 = s1 + s2;		//error, int to short as well; 
		short s4 = (short)(s1 + s2);
		short s5 = ++s1;		//ok
		s1 += 1;				//ok
		s1 = (short)(s1 + 1);	// s1 += 1即编译成这种形式，自带类型转化
		//byte同
		
		Short s = 1;
		//s += 1;			//error, 编译成 v1 = (Short)(v1 + 1), 不能把int转为Short类
	}
	
	public static void intToLong(){
		System.out.println("---------------intToLong begin");
		long ret1 = 0x100000000L + 0xcafebabeL;//32位long+long
		System.out.println((ret1 == 0x1cafebabeL) + "\t ret1 = " + Long.toHexString(ret1));
		long ret2 = 0x100000000L + 0xcafebabe;//long + int
		System.out.println((ret2 == 0x1cafebabeL) + "\t ret2 = " + Long.toHexString(ret2));
		//因为0xcafebabe最高位为1，所以是负数，int扩展为long时为符号扩展，所以结果是0xffffffffcafebabe
	}
	
	@SuppressWarnings("unused")
	public void 三元操作符中的类型转换(){
		char x = 'X';
		int i = 0;
		//:前后数据类型不一致
		System.out.println(true ? x : 0);		//'X', int字面量可以转化成char, int->char
		System.out.println(true ? x : 65535);	//'X', int字面量可以转化成char, int->char
		System.out.println(true ? x : 65536);	//88, 不能转化为char，提升到int
		System.out.println(true ? x : i);		//88, int变量i，所以char转型为int
		System.out.println(false ? i : x);		//88, int变量i，所以char转型为int
	}
	
	public static void allToChar(){
		System.out.println("---------------allToChar begin");
		int ret = (int)(char)(byte)-1;
		System.out.println(ret);
		//-1					ffffffff
		//(byte)-1				ff
		//(char)(byte)-1		ffff
		//(int)(char)(byte)-1	0000ffff
		//char无负值，不管它将要被提升成什么类型，都执行无符号零扩展
	}
	
}

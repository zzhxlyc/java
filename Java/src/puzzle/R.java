package puzzle;

public class R {
	
	//少了L的话，是按int的乘法最后再提升到long，所以会溢出
	final static long LongExpression = 24 * 60 * 60 * 1000 * 1000 * 1000L;
	
	public void 十六进制字面常数可以直接表示负数(){
		int a = 0xcafebabe;		//符号扩展为long时，前导都是1
		System.out.println((long)a == 0xcafebabeL);	//false
	}
	
	public void 字节数组和String(){
		//指定使用Unicode字符集，才是字节:字符=2:1，默认或者ISO-8859-1(1:1)或者GBK([1-2]:1)
	}
	
	// 返回值是"a", 因为finally在return之后进行, finally对sb的操作新建了"ab"对象本身"a"并不变
	public String t(){
		String sb = new String("a");
		try{
			return sb;
		}
		finally{
			sb += "b";
		}
	}
	
	public static void main(String[] args) {
		System.out.println(R.LongExpression);
	}
	
	
}

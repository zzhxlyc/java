package vm.gc;

public class 本地变量表影响GC {
	
	/*
	 * 6144K->6293K b数组无法被GC, 因为变量b存在于是本地变量表, 而本地变量表属于GC ROOT
	 */
	public static void 本地变量表影响GC例子(){
		{
			byte[] b = new byte[6 * 1024 * 1024];
			System.out.println(b);
		}
		System.gc();
	}
	
	/*
	 * 6144K->149K b数组被GC, 因为在本地变量表中a变量覆盖了b变量（b变量超出作用范围）
	 */
	public static void 本地变量表可以被覆盖(){
		{
			byte[] b = new byte[6 * 1024 * 1024];
			System.out.println(b);
		}
		byte a = 1;
		System.out.println(a);
		System.gc();
	}

	public static void main(String[] args) {
//		本地变量表影响GC例子();
		本地变量表可以被覆盖();
	}

}

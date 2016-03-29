package exception;

public class Finally {
	// field的初始化先于构造函数
	public int i = f();
	
	public Finally() {
		try {
			throw new Exception("Exception in constructor");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	//这里抛出一个StringIndexOutOfBoundsException
	static int f() {
		String s = "";
		s.charAt(1);
		return 1;
	}

	public static void main(String[] args) {
//		new Finally();
//		finallyWillBeExecuteAllTheTime();
//		normalExceptionCannotCatchIfNotThrowed();
//		System.out.println(twoReturnValue());
		catchWhichException();
	}
	
	//除了在try块中使用System.exit(0)，finally中的代码都会被执行
	public static void finallyWillBeExecuteAllTheTime(){
		int i = 0;
		System.out.println("--continue--");
		while (i++ <= 1) {
			try {
				System.out.println("i=" + i);
				continue;
			} catch (Exception e) {
			} finally {
				System.out.println("finally");//continue跳出, Executed
			}
		}
		System.out.println("--break--");
		while (i++ <= 3) {
			try {
				System.out.println("i=" + i);
				break;
			} catch (Exception e) {
			} finally {
				System.out.println("finally");//break跳出, Executed
			}
		}
		System.out.println("--return--");
		try {
			System.out.println("i=" + i);
			return;
		} catch (Exception e) {
		} finally {
			System.out.println("finally");//return跳出, Executed
		}
	}
	
	public static void normalExceptionCannotCatchIfNotThrowed(){
		try {
			new Finally();
		}
//		catch (IOException e) {  try块没有抛出Exception异常，则catch不能捕获这些异常，编译错误
//			System.out.println(e.toString());
//		}
		//RuntimeException可以随时随地用catch来捕获
		//可以捕获最高级的Exception异常，因为RuntimeException继承于此
		catch (RuntimeException e) {   
			System.out.println(e.toString());
		}
		catch (Exception e) {   
			System.out.println(e.toString());
		}
	}
	
	/*
	 * return is 3
	 * 执行完return 1;将1这个结果值写到函数调用栈返回值那个地方后，在函数返回前，再执行finally代码，
	 * 这里再次return 3;新的3这个结果就更新在函数调用栈里的返回值
	 */
	public static int twoReturnValue(){
		try {
			return 1;
		} catch (Exception e) {
			return 2;
		} finally{
			return 3;
		}
	}
	
	/*
	 * 打印出c, 所以最后抛出的异常是finally里的throw new Exception("c");
	 * 原理应该同返回值
	 */
	@SuppressWarnings("finally")
	public static void catchWhichException(){
		try {
			try {
				throw new Exception("a");
			} catch (Exception e) {
				throw new Exception("b");
			} finally{
				throw new Exception("c");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

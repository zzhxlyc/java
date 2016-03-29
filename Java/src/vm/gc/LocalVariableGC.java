package vm.gc;

import java.lang.ref.WeakReference;

public class LocalVariableGC {
	
	public static void main(String[] args) {
		局部变量表作用域对GC的影响();
		局部变量表重用对GC的影响();
	}
	
	//作用域外的局部变量失效后, 若无对局部变量表的更新，则过了作用域的引用继续存在，无法GC
	//这种情况下，当函数体需要执行很长时间而且内存使用紧张下，在作用域外显示将arrays = null就会比较必要
	public static void 局部变量表作用域对GC的影响(){
		WeakReference<byte[]> ref = null;
		{
			byte[] arrays = new byte[4 * 1024 * 1024];
			ref = new WeakReference<byte[]>(arrays);
		}
		System.gc();
		System.out.println(ref.get());	//没有被GC, 因为局部变量表保持有对arrays的引用
	}
	
	//作用域外的局部变量失效后, 局部变量表重用可以导致失效的引用被覆盖而失去对arrays的引用而被GC
	//但必须有新的局部变量的作用才会导致局部变量表重用, 否则失效引用一直存在直到函数返回
	public static void 局部变量表重用对GC的影响(){
		WeakReference<byte[]> ref = null;
		{
			byte[] arrays = new byte[4 * 1024 * 1024];
			ref = new WeakReference<byte[]>(arrays);
		}
		int a = 1;	//有了此句， 局部变量表被再次使用, 失效引用arrays被覆盖从而被GC
		System.gc();
		System.out.println(a + " " + ref.get());
	}

}

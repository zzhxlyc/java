package vm.gc;

public class SaveByFinalize {
	
	private static SaveByFinalize save_ptr = null;
	
	//finalize执行代价极大, 避免使用
	//finalize被VM开启的低优先级Finalizer线程来执行, 而不是被main线程执行
	@Override
	protected void finalize() throws Throwable {
		long id = Thread.currentThread().getId();
		System.out.println(id + " : SaveByFinalize::finalize");
		super.finalize();
		save_ptr = this;
	}

	public static void main(String[] args) throws InterruptedException {
		SaveByFinalize.save_ptr = new SaveByFinalize();
		
		//第一次GC, 执行了finalize, 没有被GC
		SaveByFinalize.save_ptr = null;
		System.gc();
		Thread.sleep(1000);		//Finalizer线程优先级低
		long id = Thread.currentThread().getId();
		System.out.println(id + " : " + SaveByFinalize.save_ptr);
		
		//第二次GC, 没有执行finalize, 被GC
		save_ptr = null;
		System.gc();
		Thread.sleep(1000);
		System.out.println(id + " : " + SaveByFinalize.save_ptr);
	}

}

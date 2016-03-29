package base;

public class Block {
	/*
	 * 静态块，在加载class类时候被执行，按先后顺序执行
	 */
	static{
		System.out.println("static block1");
	}
	
	static{
		System.out.println("static block2");
	}
	
	T t = new T(1);
	/*
	 * 普通块，在new操作时候被执行, 与field一起按先后顺序被执行, 先于构造函数
	 */
	{
		System.out.println("block1");
	}
	T t2 = new T(2);
	
	public Block(){
		System.out.println("Construction");
	}
	
	public static void main(String[] args) {
		System.out.println(Block.class);
		System.out.println("new function");
		new Block();
	}
	
	{
		System.out.println("block2");
	}
	
	static{
		System.out.println("static block3");
	}
	
	class T{
		public T(int i) {
			System.out.println("T() : " + i);
		}
	}

}

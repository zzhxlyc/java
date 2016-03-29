package warning;

//和文件名相同的主要的类, 可以不是public的
class Warning {

	public static void main(String[] args) {
		System.out.println("Warning.main(String[] args)");
	}

}

//class名可以小写（Eclipse推荐大写）
class main{
	//构造
	public main(){}
	
	//Warning：和构造名称一样的函数
	public static void main() {}
	
	
	//真正的main函数，必须带String[]参数（无参的main函数就是main函数的一个重载，不会被系统自动加载掉用）
	//Warning：和构造名称一样的函数
	public static void main(String[] args) {
		System.out.println("main.main(String[] args)");
	}
}

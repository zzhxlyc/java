package base.classType;

public class AllTypeClass {
	
								//匿名类, 直接对接口进行new
								//编译：外部类$数字.class，AllTypeClass$1.class
	Interface anonymousClass = new Interface(){
		//...
	};
	
	//内部类，其对象含有对外部类对象的引用, 需要Object实例才能new
	//编译：外部类$内部类.class，AllTypeClass$MemberClass.class
	public class MemberClass{
		//内部类不能拥有静态字段和嵌套类，因为它是属于外部类的每个实例的，而不属于外部类的那个类
	}
	
	//静态内部类，其实和外部类基本无关..., 直接用OuterClass.InnerClass就可以new
	//编译：外部类$静态内部类.class，AllTypeClass$StaticMemberClass.class
	public static class StaticMemberClass{};
	
	public void method(){
		//局部类
		//类外方法是object的还是static的都一样
		//class 修饰符 only abstract or final, 不能有静态的field和函数
		//编译：外部类$n内部类.class，AllTypeClass$1LocalClass.class
		class LocalClass{
			public int a;
			public int getA(){return a;}
		}
		new LocalClass().getA();
	}
	
	public static void main(String[] args) {
		new AllTypeClass.StaticMemberClass();	//静态内部类
//		new AllTypeClass.MemberClass();			//error
		new AllTypeClass().new MemberClass();	//内部类
		
		new Interface.AA();						//静态内部类
		new Interface.InnerInterface(){
			//...
		};		//匿名类
	}
}

//接口内部的接口和类都默认是静态的
interface Interface{
	class AA{}
	interface InnerInterface{}
}

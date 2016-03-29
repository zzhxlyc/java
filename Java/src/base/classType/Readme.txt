内部类：
与外部类有紧密的联系，隐式的保存了一个对外面对象的引用
内部类不能拥有静态字段和嵌套类，因为它是属于外部类的某个实例的，而不属于外部类的类对象
类内部可以直接创建内部类，因为方法内(除静态方法)有this引用
类外部要创建内部类，必须用InnerClass inner = obj.new InnerClass() 来创建

嵌套类(静态内部类)：
与外部类没有直接关系(通过static关键字体现)，但它属于这个外部类（相当于属于一个名称空间）。
在类外部创建嵌套类时，需要外部类的命名空间, new OutClass.NestedClass()
可以被实现在接口中，接口中的类默认为public和static的
适合被用来作为一个类的测试而取代main方法，发布时只需删除嵌套类对应的class文件，对主类的class文件没有任何影响

匿名内部类：
public interface face;
return new face() {
	@Overrid
	public void f(){
	}
}
这也是一个类，有对应的class文件，命名为"当前类名$序号"：AnonymousClass$1.class
new了一个face的实现对象(即implement face的类)，然后向上转型为face对象返回
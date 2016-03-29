package base;

public class Overload {
	
	/**
	 * Java的重载解析过程是分两阶段运行的
	 * 第一阶段选取所有可获得并且可应用的方法或构造器
	 * 第二阶段在第一阶段选取的方法或构造器中选取最精确的一个
	 * 如果方法A可以接受传递给方法B的任何参数(构造器同)，
	 * 那么我们就说方法A比方法B缺乏精确性，调用时就会选取方法B
	 */

	//以Object为参数的方法可以接受double[]为参数，所以较下面的方法缺乏精确性
	private void confusing(Object o) {
		System.out.println("Object");
	}

	//较之上面那个方法，更为精确，所以优先调用
	private void confusing(double[] dArr) {
		System.out.println("double array");
	}

	public static void main(String[] args) {
		new Overload().confusing(null);//2个都可以接受，选择double[]更精确
		new Overload().confusing(5.4);//boxing后可以被Object接受
		new Overload().confusing((Object)null);//只能被Object接受
	}

}

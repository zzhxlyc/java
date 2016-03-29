package operator;

public class Priority {
	
	public static void main(String[] args) {
		f();
	}
	
	static void f(){
		String a = "hello";
		String b = "hello";
		System.out.println(a==b + " is the result");
		//false, 加号优先级比==号高，相当于：a == (b + " is the result")
	}

}

package base;

public class VariableArgs {

	//nums是个int数组对象, 编译成 public int sum(int[] nums)
	//可以接受1, 2, 3这样的多个参数, 也可以接受int数组参数
	public int sum(int... nums) {
		int sum = 0;
		if(nums == null){
			return 0;
		}
		for (int num : nums) {
			sum += num;
		}
		return sum;
	}
	
	//函数编译不通过，和第一个是一样的
	//只能接受int数组参数
//	public int sum(int[] nums) {
//		int sum = 0;
//		for (int num : nums) {
//			sum += num;
//		}
//		return sum;
//	}
	
	//与第一个版本的函数冲突 函数本身编译通过（可同时存在），函数调用编译不通过（不知道调用哪个）
//	public int sum(int a, int... nums) {
//		int sum = 0;
//		if(nums == null){
//			return 0;
//		}
//		for (int num : nums) {
//			sum += num;
//		}
//		return sum;
//	}
	
	public String sum() {return "this no args";}
	


	public static void main(String[] args) {
		VariableArgs v = new VariableArgs();
		System.out.println(v.sum());
		System.out.println(v.sum(null));	//数组也是对象，null也可以看成数组
		System.out.println(v.sum(1));
		System.out.println(v.sum(1,2,3,5,7,0,45,-66,43,325));
		System.out.println(v.sum(new int[]{1,2}));
	}
}

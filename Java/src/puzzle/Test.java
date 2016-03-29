package puzzle;

public class Test {
	
	public static String t(){
		String sb = new String("abc");
		try{
			return sb;
		}
		finally{
			sb += "a";
		}
	}
	
	public static void main(String[] args) {
		System.out.println(t());
	}

}

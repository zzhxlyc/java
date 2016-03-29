package security;

import java.util.Date;
import java.util.Random;

public class KeyGenerator {
	
	private static Random r = new Random();
	
	public static String getKey(){
		Date d = new Date();
		return MD5Helper.MD5Encode("" + d.getTime() + r.nextInt(Integer.MAX_VALUE));
	}
	
	public static void main(String[] args) {
		System.out.println(KeyGenerator.getKey());
		System.out.println(KeyGenerator.getKey());
		System.out.println(KeyGenerator.getKey());
		System.out.println(KeyGenerator.getKey());
	}
	
}

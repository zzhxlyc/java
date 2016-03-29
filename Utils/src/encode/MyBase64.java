package encode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class MyBase64 {
	
	public static void main(String[] args) throws Exception {
		decodeByApache();
	}
	
	public static void encodeByApache() throws Exception{
		System.out.println("please input user name:");
		String username = new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.println(new String(Base64.encodeBase64(username.getBytes()), Charset
                .forName("UTF-8")));
		System.out.println("please input password:");
		String password = new BufferedReader(new InputStreamReader(System.in)).readLine();		
		System.out.println(new String(Base64.encodeBase64(password.getBytes()), Charset
                .forName("UTF-8")));		
	}
	
	public static void encodeBySun() throws Exception{
		BASE64Encoder encoder = new BASE64Encoder();
		System.out.println("please input user name:");
		String username = new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.println(encoder.encode(username.getBytes()));
		System.out.println("please input password:");
		String password = new BufferedReader(new InputStreamReader(System.in)).readLine();		
		System.out.println(encoder.encode(password.getBytes()));		
	}
	
	public static void decodeByApache() throws Exception{
		System.out.println("please input user name:");
		String username = new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.println(new String(Base64.decodeBase64(username.getBytes()), Charset
                .forName("UTF-8")));
		System.out.println("please input password:");
		String password = new BufferedReader(new InputStreamReader(System.in)).readLine();		
		System.out.println(new String(Base64.decodeBase64(password.getBytes()), Charset
                .forName("UTF-8")));
	}
	
	public static void decodeBySun() throws Exception{
		BASE64Decoder decoder = new BASE64Decoder();
		System.out.println("please input user name:");
		String username = new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.println(new String(decoder.decodeBuffer(username)));	
		System.out.println("please input password:");
		String password = new BufferedReader(new InputStreamReader(System.in)).readLine();		
		System.out.println(new String(decoder.decodeBuffer(password)));		
	}
}

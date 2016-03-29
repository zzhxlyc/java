package web;

import java.util.HashMap;
import java.util.Map;

public class URLParamParser {
	
	public static Map<String, String> parse(String queryString){
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (String s : queryString.split("&")) {
				String[] result = s.split("=");
				map.put(result[0], result[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static void main(String[] args) {
		Map<String, String> map = URLParamParser.parse("user=admin&pwd=123456");
		System.out.println(map);
	}
}

package http;

public class HTTPParser {

	public static final String GET = "GET";
	public static final String POST = "POST";
	private static final String UNSUPPORT = "UNSUPPORT";

	private String method = UNSUPPORT;
	private String path = UNSUPPORT;
	private String host = UNSUPPORT;
	private String data = UNSUPPORT;
	private boolean ok = true;

	public HTTPParser(String request) {
		String[] s = request.split("\r\n");
		if (s.length > 2) {
			parseFristLine(s[0]);
			parseSecondLine(s[1]);
			parseData(request);
		}
	}

	private void parseFristLine(String line) {
		char frist = line.charAt(0);
		switch (frist) {
		case 'G':
			method = GET;
			break;
		case 'P':
			method = POST;
			break;
		default:
			method = UNSUPPORT;
		}
		if (method.equals(GET)) {
			path = line.substring(4, line.indexOf("HTTP") - 1);
		} else if (method.equals(POST)) {
			path = line.substring(5, line.indexOf("HTTP") - 1);
		} else {
			path = UNSUPPORT;
		}
		if (!path.equals("localhost")) {
			ok = false;
		}
	}

	private void parseSecondLine(String line) {
		host = line.substring(6);
	}
	
	private void parseData(String s){
		String cl = "Content-Length: ";
		int i = s.indexOf(cl);
		if(i > 0){
			char num0 = s.charAt(i + cl.length());
			if(num0 - '0' > 0){
				data = s.substring(s.indexOf("\r\n\r\n") + 4);
			}
		}
	}

	public String getMethod() {
		return method;
	}

	public String getPath() {
		if (path.equals("/")) {
			path = "/index.html";
		}
		return path;
	}

	public String getHost() {
		return host;
	}

	public String getData() {
		return data;
	}

	public boolean isOk() {
		return ok;
	}

	public static void main(String[] args) {
		test2();
	}

	public static void test1() {
		HTTPParser s = new HTTPParser(
						  "GET /home.do HTTP/1.1\r\n"
						+ "Host: localhost\r\n"
						+ "Connection: keep-alive\r\n"
						+ "User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.55 Safari/533.4\r\n"
						+ "Accept: application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5\r\n"
						+ "Accept-Encoding: gzip,deflate,sdch\r\n"
						+ "Accept-Language: zh-CN,zh;q=0.8\r\n"
						+ "Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n"
						+ "Cookie: uid=3072211106\r\n");
		System.out.println("Method : " + s.getMethod());
		System.out.println("Path : " + s.getPath());
		System.out.println("Host : " + s.getHost());
	}
	
	public static void test2() {
		HTTPParser s = new HTTPParser(
						  "POST /post.do HTTP/1.1\r\n"
						+ "Host: localhost\r\n"
						+ "User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.55 Safari/533.4\r\n"
						+ "Accept: application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5\r\n"
						+ "Accept-Encoding: gzip,deflate,sdch\r\n"
						+ "Accept-Language: zh-CN,zh;q=0.8\r\n"
						+ "Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n"
						+ "Connection: keep-alive\r\n"
						+ "Referer: http://localhost/\r\n"
						+ "Cookie: uid=3072211106\r\n"
						+ "Content-Type: application/x-www-form-urlencoded\r\n"
						+ "Content-Length: 16\r\n"
						+ "\r\n"
						+ "user=123&pwd=321"
						);
		System.out.println("Method : " + s.getMethod());
		System.out.println("Path : " + s.getPath());
		System.out.println("Host : " + s.getHost());
		System.out.println("Data : " + s.getData());
	}

}

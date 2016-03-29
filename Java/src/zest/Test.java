package zest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Test {

	public static void main(String[] args) throws Exception {
		for (int i = 21121000; i < 21121200; i++) {
			download("http://ecard.zju.edu.cn/vpic.php?id=" + i, "" + i);
		}
	}

	public static void download(String urlString, String filename)
			throws Exception {
		URL url = new URL(urlString);
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();

		if (is.available() < 10) {
			System.out.println(filename + " pass!");
			return;
		}

		byte[] bs = new byte[1024];
		int len;
		File file = new File("E://temp//" + filename + ".jpg");
		OutputStream os = new FileOutputStream(file);
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		os.close();
		is.close();
		System.out.println(filename + " done!");
	}

}

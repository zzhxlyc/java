package image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class 照片缩放 {
	
	public static final int WIDTH = 800;
	public static final String SPLIT = "\\";
	public static final String TEMP = "Temp";

	public static void main(String[] args) {
		MyTimer timer = new MyTimer();
		timer.start();
		int num = 0;
		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("input the source folder path");
			String source = scan.nextLine();
			File fs = new File(source);
			if(!fs.exists()){
				throw new FileNotFoundException(source);
			}
			File ds = new File(fs.getAbsolutePath() + SPLIT + TEMP);
			File[] list = fs.listFiles();
			num = list.length;
			if(!ds.exists()){
				ds.mkdir();
			}
			int done = 0;
			System.out.println("共" + num + "个文件");
			for (File file : list) {
				if(!file.getName().equals(TEMP)){
					resizeImage(file, ds);
					done++;
					System.out.println(" ...还剩" + (num - done));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		timer.end();
		System.out.println(timer.getTimeString());
		int per = (int)(timer.getTime() / num);
		System.out.println(per + " in average for each");
	}
	
	public static void resizeImage(File imgFile, File folder){
		try {
			InputStream is = new FileInputStream(imgFile);
			String name = folder.getAbsolutePath() + SPLIT + imgFile.getName();
			File newFile = new File(name);
			newFile.createNewFile();
			OutputStream os = new FileOutputStream(newFile);
			ImageUtil.resizeJPEG(is, os, WIDTH);
			is.close();
			os.close();
			System.out.print(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

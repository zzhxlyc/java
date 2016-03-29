package image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CreateImage {
	
	public static BufferedImage getImage() {
		// image初始化
		BufferedImage image = new BufferedImage(60, 30,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		// 图片各属性设置
		graphics.setColor(new Color(0, 255, 0));
		graphics.drawRect(60, 30, 60, 30);
		graphics.fillRect(0, 0, 60, 30);
		graphics.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		// 图片中插入字母
		for (int i = 0; i < 4; i++) {
			String temp = "degx".substring(i, i + 1);
			graphics.setColor(new Color(102, 32, 176));
			graphics.drawString(temp, 13 * i + 6, 16);
		}
		graphics.dispose();
		return image;
	}

	public static void main(String[] args) throws ImageFormatException,
			IOException {
		FileOutputStream fos = new FileOutputStream("c:\\test.jpg");
		JPEGImageEncoder jie = JPEGCodec.createJPEGEncoder(fos);
		jie.encode(CreateImage.getImage());
	}

}

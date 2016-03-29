package image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageUtil {
	
	public static void main(String[] args) throws Exception {
		InputStream is = ImageUtil.class.getResourceAsStream("newproj.jpg");
		FileOutputStream os = new FileOutputStream(new File("11.jpg"));
		resizeJPEG(is, os, 250);
	}
	
	public static String getImageType(InputStream is) throws IOException{
		byte[] buffer = new byte[10];
		if(is.read(buffer) < 10){
			throw new IOException("not valid image");
		}
		is.close();
		byte b0 = buffer[0];
		byte b1 = buffer[1];
		byte b2 = buffer[2];
		byte b3 = buffer[3];
		byte b6 = buffer[6];
		byte b7 = buffer[7];
		byte b8 = buffer[8];
		byte b9 = buffer[9];
		String type = "";
		if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F')
			type = "gif";
		else if (b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G')
			type = "png";
		else if (b6 == (byte) 'J' && b7 == (byte) 'F' 
				&& b8 == (byte) 'I' && b9 == (byte) 'F')
			type = "JPEG";
		else if (b0 == (byte) 'B' && b1 == (byte) 'M')
			type = "bmp";
		else {
			type = "unknown";
		}
		return type;
	}
	
	public static String getImageType2(InputStream is) throws IOException{
        ImageInputStream iis = ImageIO.createImageInputStream(is);
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        if (!iter.hasNext()) {
        	throw new IOException("not valid image");
        }
        ImageReader reader = iter.next();
        iis.close();
        is.close();
        return reader.getFormatName();
	}
	
	public static Map<String, Object> getImageProperty(InputStream is) throws IOException{
        ImageInputStream iis = ImageIO.createImageInputStream(is);
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        if (!iter.hasNext()) {
        	throw new IOException("not valid image");
        }
        ImageReader reader = iter.next();
        reader.setInput(iis, true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", reader.getFormatName());
        map.put("width", reader.getWidth(0));
        map.put("height", reader.getHeight(0));
        return map;
	}
	
	private static BufferedImage resize(InputStream is, int newWidth) throws IOException{
		Image image = ImageIO.read(is);
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		if (width == -1 || width == 0) {
			throw new IOException("not valid image");
		}
		int newHeight = (int)((double)newWidth / width * height);
		
		BufferedImage bufferedImage = new BufferedImage(newWidth,
				newHeight, BufferedImage.TYPE_INT_RGB);
		bufferedImage.getGraphics().drawImage(
				image.getScaledInstance(newWidth, newHeight,
						Image.SCALE_SMOOTH), 0, 0, null);
		return bufferedImage;
	}
	
	public static void resizeGIF(InputStream is, OutputStream os, int newWidth) throws IOException{
		BufferedImage bufferedImage = resize(is, newWidth);
		ImageIO.write(bufferedImage, "GIF", os);
		is.close();
		os.close();
	}
	
	public static void resizePNG(InputStream is, OutputStream os, int newWidth) throws IOException{
		BufferedImage bufferedImage = resize(is, newWidth);
		ImageIO.write(bufferedImage, "PNG", os);
		is.close();
		os.close();
	}
	
	public static void resizeJPEG(InputStream is, OutputStream os, int newWidth) throws IOException{
		BufferedImage bufferedImage = resize(is, newWidth);
		ImageIO.write(bufferedImage, "JPEG", os);
		is.close();
		os.close();
	}
	
	public static void test_getImageProperty(InputStream is) throws IOException{
		Map<String, Object> map = ImageUtil.getImageProperty(is);
		for (Entry<?, ?> e : map.entrySet()) {
			System.out.println(e.getKey() + " : " + e.getValue());
		}
	}
	
}

package image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ImageManage {
	
	private byte[] bytes;
	
	private String type;
	private int width;
	private int height;
	
	public ImageManage(InputStream is) throws IOException {
		this.bytes = getImgByteArray(is);
		this.type = getImageType();
		InputStream stream = getInputStreamFromBytes();
		getImageProperty(stream);
	}

	private byte[] getImgByteArray(InputStream is) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(is);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int c = -1;
		while ((c = bis.read()) != -1) {
			baos.write(c);
		}
		bis.close();
		baos.close();
		return baos.toByteArray();
	}
	
	public InputStream getInputStreamFromBytes() throws IOException {  
        InputStream ret = null;  
        if (bytes == null || bytes.length <= 0) {  
            return ret;  
        }  
        return new ByteArrayInputStream(bytes);  
    }
	
	private String getImageType() throws IOException {
		byte b0 = bytes[0];
		byte b1 = bytes[1];
		byte b2 = bytes[2];
		byte b3 = bytes[3];
		byte b6 = bytes[6];
		byte b7 = bytes[7];
		byte b8 = bytes[8];
		byte b9 = bytes[9];
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
			throw new IOException("not valid image");
		}
		return type;
	}
	
	private void getImageProperty(InputStream is) throws IOException{
		BufferedImage bufferedImage = ImageIO.read(is);
		this.width = bufferedImage.getWidth();
		this.height = bufferedImage.getHeight();
	}
	
	public void resize(OutputStream os, int w, int h) throws IOException{
		Image image = ImageIO.read(getInputStreamFromBytes());
		if (width == -1 || width == 0) {
			throw new IOException("not valid image");
		}
		int newWidth = 0, newHeight = 0;
		if(w / h > (width / width)){
			newHeight = h;
			newWidth = (int)((double)h / height * width);
		}
		else{
			newWidth = w;
			newHeight = (int)((double)w / width * height);
		}
		
		BufferedImage bufferedImage = new BufferedImage(newWidth,
				newHeight, BufferedImage.TYPE_INT_RGB);
		bufferedImage.getGraphics().drawImage(
				image.getScaledInstance(newWidth, newHeight,
						Image.SCALE_SMOOTH), 0, 0, null);
		if(type.equalsIgnoreCase("JPEG")){
			ImageIO.write(bufferedImage, "JPEG", os);
		}
		else if(type.equalsIgnoreCase("GIF")){
			ImageIO.write(bufferedImage, "GIF", os);
		}
		else if(type.equalsIgnoreCase("PNG")){
			ImageIO.write(bufferedImage, "PNG", os);
		}
		os.close();
	}

	public String getType() {
		return type;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public byte[] getBytes() {
		return bytes;
	}

	public static void main(String[] args) {
		try {
			InputStream is = ImageManage.class.getResourceAsStream("newproj.PNG");
			OutputStream os = new FileOutputStream(new File("111.PNG"));
			ImageManage im = new ImageManage(is);
			System.out.println(im.getType());
			System.out.println(im.getWidth());
			System.out.println(im.getHeight());
			im.resize(os, 200, 65);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

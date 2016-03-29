package h.io.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;

public class CompressionExample {
	
	public void compress(){
		try {
			FileInputStream fis = new FileInputStream(new File("a.txt"));
			CompressionCodec codec = new BZip2Codec();
			FileOutputStream fos = new FileOutputStream(new File("a.txt" + codec.getDefaultExtension()));
			OutputStream os = codec.createOutputStream(fos);
			IOUtils.copyBytes(fis, os, 4096, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void decompress(){
		try {
			Configuration conf = new Configuration();
			CompressionCodecFactory factory = new CompressionCodecFactory(conf);
			String compress_file = "a.txt.bz2";
			CompressionCodec codec = factory.getCodec(new Path(compress_file));
			FileInputStream fis = new FileInputStream(new File(compress_file));
			InputStream is = codec.createInputStream(fis);
			FileOutputStream fos = new FileOutputStream(new File("a.txt"));
			IOUtils.copyBytes(is, fos, 4096, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class Encryptor {

	private static Logger logger = Logger.getLogger(Encryptor.class);

	/**
	 * @param str
	 * @return
	 * @Description: SHA1
	 */
	public static String SHA1(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuilder hexString = new StringBuilder();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (Exception e) {
			logger.error("SHA1 error", e);
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @param str
	 * @return
	 * @Description: SHA
	 */
	public static String SHA(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance("SHA");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (Exception e) {
			logger.error("SHA error", e);
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @param str
	 * @return
	 * @Description: 32位小写MD5
	 */
	public static String MD5(String str) {
		return md5L32(str);
	}

	/**
	 * @param str
	 * @return
	 * @Description: 32位小写MD5
	 */
	public static String md5L32(String str) {
		String reStr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(str.getBytes());
			StringBuilder stringBuffer = new StringBuilder();
			for (byte b : bytes) {
				int bt = b & 0xff;
				if (bt < 16) {
					stringBuffer.append(0);
				}
				stringBuffer.append(Integer.toHexString(bt));
			}
			reStr = stringBuffer.toString();
		} catch (Exception e) {
			logger.error("MD5 error", e);
			e.printStackTrace();
		}
		return reStr;
	}

	/**
	 * @param str
	 * @return
	 * @Description: 32位大写MD5
	 */
	public static String md5U32(String str) {
		String reStr = md5L32(str);
		if (reStr != null) {
			reStr = reStr.toUpperCase();
		}
		return reStr;
	}

	/**
	 * @param str
	 * @return
	 * @Description: 16位小写MD5
	 */
	public static String md5U16(String str) {
		String reStr = md5L32(str);
		if (reStr != null) {
			reStr = reStr.toUpperCase().substring(8, 24);
		}
		return reStr;
	}

	/**
	 * @param str
	 * @return
	 * @Description: 16位大写MD5
	 */
	public static String md5L16(String str) {
		String reStr = md5L32(str);
		if (reStr != null) {
			reStr = reStr.substring(8, 24);
		}
		return reStr;
	}

	/**
	 * AES加密
	 */
	public static byte[] AESEncrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("UTF-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (Exception e) {
			logger.error("AESEncrypt error", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES解密
	 */
	public static byte[] AESDecrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			return cipher.doFinal(content);
		} catch (Exception e) {
			logger.error("AESDecrypt error", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES解密后按UTF8组成字符串
	 */
	public static String AESDecryptUTF8(byte[] content, String password) {
		byte[] bs = AESDecrypt(content, password);
		try {
			return new String(bs, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("AESDecryptUTF8 error", e);
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * DES加密
	 */
	public static byte[] DESEncrypt(String content, String key) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			byte[] result = cipher.doFinal(content.getBytes("UTF-8"));
			return result;
		} catch (Exception e) {
			logger.error("DESEncrypt error", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DES解密
	 */
	public static byte[] DESDecrypt(byte[] content, String key) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			return cipher.doFinal(content);
		} catch (Exception e) {
			logger.error("DESDecrypt error", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DES解密后按UTF8组成字符串
	 */
	public static String DESDecryptUTF8(byte[] content, String password) {
		byte[] bs = DESDecrypt(content, password);
		try {
			return new String(bs, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("DESDecryptUTF8 error", e);
			e.printStackTrace();
			return "";
		}
	}

	public static String Base64Encode(final byte[] bytes) {
		return new String(Base64.encodeBase64(bytes));
	}

	public static byte[] Base64Decode(final byte[] bytes) {
		return Base64.decodeBase64(bytes);
	}

	public static byte[] Base64DecodeUTF8(String str) {
		try {
			return Base64.decodeBase64(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("Base64DecodeUTF8 error", e);
			e.printStackTrace();
			return null;
		}
	}

}

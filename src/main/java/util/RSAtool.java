package util;

import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * RSA加密解密
 * 
 * encrypt(String) 输入:待加密的文本 输出:加密后的文本
 * 
 * decrypt(String) 输入:加密后的文本 输出:解密后的文本
 *
 */
public class RSAtool {

//	private String RSA_PUFILE = ""; // rsa公钥文件保存位置
	private String RSA_PUBLIC = "-----BEGIN PUBLIC KEY-----\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnGcSoZJN08opkzHKOw6b\n"
			+ "s+5lqp0C3m8dExLm9HZuoPOlzDuSAl6pNQr8Dwg9szHbqnY3AUGinCHUKGkOKTyM\n"
			+ "Cg6Q3hst2DZLm5JNAAd+SomWkNLpd+MismCyLLQQEsDJYnywhs19i9nD1XBu2oh0\n"
			+ "fyggS8++DzIdCD/yvQkQ9COf2QroMSBCNNkasCsuw3oYMWVYdcwNkT4ETIqTJMrK\n"
			+ "uMm+ma/5nr8dBGDdEPeZhMV5LFlroZXpBpD8nYBQWpGftq4udJ70448074W5P59g\n"
			+ "bymKbPBfMDK6SHJcc5BT0TQ+qi51a0e/4RIW0JEzBQIScOLRIdB6czwqUczxt2Bt\n" 
			+ "EQIDAQAB\n"
			+ "-----END PUBLIC KEY-----\n"; // rsa公钥
//	private String RSA_PRFILE = ""; // rsa私钥文件保存位置
	private String RSA_PRIVATE = "-----BEGIN PRIVATE KEY-----\n"
			+ "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCcZxKhkk3TyimT\n"
			+ "Mco7Dpuz7mWqnQLebx0TEub0dm6g86XMO5ICXqk1CvwPCD2zMduqdjcBQaKcIdQo\n"
			+ "aQ4pPIwKDpDeGy3YNkubkk0AB35KiZaQ0ul34yKyYLIstBASwMlifLCGzX2L2cPV\n"
			+ "cG7aiHR/KCBLz74PMh0IP/K9CRD0I5/ZCugxIEI02RqwKy7DehgxZVh1zA2RPgRM\n"
			+ "ipMkysq4yb6Zr/mevx0EYN0Q95mExXksWWuhlekGkPydgFBakZ+2ri50nvTjjzTv\n"
			+ "hbk/n2BvKYps8F8wMrpIclxzkFPRND6qLnVrR7/hEhbQkTMFAhJw4tEh0HpzPCpR\n"
			+ "zPG3YG0RAgMBAAECggEAEhbp1L4zOje7r0am+UWrRJhiMeWPG2MGndol1EiOaxdD\n"
			+ "KegJ7zQ7peLrXGG7oSo3GfQqUH7i6+QNwc7E0IgsOk2M++Nw3f0Jqai2NzamqMBZ\n"
			+ "Jc7/KdqffLoqYXlCEqkxoa6CTFUgHDjHqbwjVitKLhraAWq5tNA7A2VCEhMmXtZB\n"
			+ "gqUPFMAtxkIz/LfOslSwmkdfp/nnTMyONllnJTQw8qAh8rRIWNnHxDICU5oCCFpG\n"
			+ "KTVAY8rQFdHRuo/X01JBdnlv/5UO6iQ7Uxy7/FYYM/KmvbcisBmmTmIbT6TAjNRz\n"
			+ "l43HQftWFC6doWEvtdi7dzZ2PXMGBNnnkKKWVTQDAQKBgQC3IMrrF08a2EOHuDVs\n"
			+ "o9WCmpXAtIbtThz+P9rFWMj0FkIGVDlYHA0wpyxeR8OIUGOp7P0ZCwsu+GrxSspI\n"
			+ "PzJY69T8rmObuJs3MVJNPT8CFZxBaBMl/NyZErQd23k5vzP7X4e4IVCl86uQWc7l\n"
			+ "Z+3F0qfD9K2oUoFXNSJ8AAOc+QKBgQDao8OlOs32GxCz1Rz0cNqoREUxYH3BEOEo\n"
			+ "iPwUoeF6ireogTvl1DgMlZ0bTbe6IBucmvkcYXo/6RGpGRvPiA0dKMZVROmrD47k\n"
			+ "gLta85TEI/V8V3ov56xfWe8XOR5BVCmWPPXxk+iCXrgx5XCJFj3/ngfNamzej4IS\n"
			+ "Ym+YLADO2QKBgEei/14ptxs0QG6GI/gEuxAlMrG4rROFvBfFmOocSjgZwOhC6E/M\n"
			+ "YWiH7PZQsm/Pqr0dZQD37f8I1KThcfIDLsnUL3RNL9AJzkYv+BcY/13sYDzB3sO8\n"
			+ "f/stcuI/KdPqNQZ/n1LrTWYtxQ5p42KgXkZFmrUwMQKoi8o2ke/Z/Y/ZAoGBAITw\n"
			+ "Hf1RC/of6r/tTHW0SL8XA5tKOCwzvzOOVveUliyUO6dRLMJJjurX7Xa0gonBDAjk\n"
			+ "kaMYAeTqKbrPcYF2D8MzD6S5CfrgstltDH34rPO1iYXZCoomK0CuxhkwuZyvN7oJ\n"
			+ "xuDkNfNO9GAiSZKQ25GY3NWhaCDK0SOshn3YA4XBAoGAEVV4lGi0H9ZGnkU/SWM9\n"
			+ "dyRH7kYw76/1/XfC/oIE7EpRW7PFerM21NvrDOl7GGd6xQ7AR4L9jw6hCGmIzEGc\n"
			+ "TkEWc+1c/4sRphjE3AkPGBR8NiEN5jBNy745em0DBEkHfqESYxwpctoZBB4P82fY\n" 
			+ "2oEjs5pQdAlJmOvGHVY5Yaw=\n"
			+ "-----END PRIVATE KEY-----\n" + ""; // rsa私钥

	/**
	 * 初始化 本应在rsa公钥文件保存位置读取,不存在就自动创建rsa公钥和密钥 但因路径问题,这里直接填入私钥,网页里直接填入公钥
	 */
	File file;

	public RSAtool() {
	}

	public String encrypt(String text) {
		String en_text = null;
		try {
			en_text = encrypt(text, RSA_PRIVATE);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return en_text;
	}

	public String decrypt(String text) {
		String de_text = null;
		try {
			de_text = decrypt(text, RSA_PRIVATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return de_text;
	}

	// RSA加密
	private String encrypt(String str, String privatekey) throws Exception {
		// base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privatekey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
				.generatePublic(new X509EncodedKeySpec(decoded));
		// RAS加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
		return outStr;
	}

	// RSA解密
	private String decrypt(String str, String privateKey) throws Exception {
		// Base64解码加密后的字符串
		byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
//		// Base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privateKey);
		PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
		// RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}
}

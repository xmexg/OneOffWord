package util;

import java.util.Random;

/**
 * 随机字符
 *
 */
public class Randomtool {
	private static char letter[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private static char LETTER[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private static char lettern[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
	private static char LETTERn[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};
	private static char Letter[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private static char Lettern[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};
	
	/**
	 * 大小写加数字
	 */
	public static String getLettern(int num) {
		Random random = new Random();
		StringBuffer randonsb = new StringBuffer();
		for(int i = 0; i < num; i++) {
			randonsb.append(Lettern[random.nextInt(Lettern.length)]);
		}
		return randonsb.toString();
	}
	
	/**
	 * 大写
	 */
	public static String getLETTER(int num) {
		Random random = new Random();
		StringBuffer randonsb = new StringBuffer();
		for(int i = 0; i < num; i++) {
			randonsb.append(LETTER[random.nextInt(LETTER.length)]);
		}
		return randonsb.toString();
	}
	
	/**
	 * 小写
	 */
	public static String getletter(int num) {
		Random random = new Random();
		StringBuffer randonsb = new StringBuffer();
		for(int i = 0; i < num; i++) {
			randonsb.append(letter[random.nextInt(letter.length)]);
		}
		return randonsb.toString();
	}
	
	/**
	 * 大写加数字
	 */
	public static String getLETTERn(int num) {
		Random random = new Random();
		StringBuffer randonsb = new StringBuffer();
		for(int i = 0; i < num; i++) {
			randonsb.append(LETTERn[random.nextInt(LETTERn.length)]);
		}
		return randonsb.toString();
	}
	
	/**
	 * 小写加数字
	 */
	public static String getlettern(int num) {
		Random random = new Random();
		StringBuffer randonsb = new StringBuffer();
		for(int i = 0; i < num; i++) {
			randonsb.append(lettern[random.nextInt(lettern.length)]);
		}
		return randonsb.toString();
	}
	
	/**
	 * 大小写
	 */
	public static String getLetter(int num) {
		Random random = new Random();
		StringBuffer randonsb = new StringBuffer();
		for(int i = 0; i < num; i++) {
			randonsb.append(Letter[random.nextInt(Letter.length)]);
		}
		return randonsb.toString();
	}
}

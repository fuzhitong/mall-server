package cn.enjoy.core.utils;

import java.security.SecureRandom;

/**
 * 生成暗号
 * @author haoss
 *
 */
public class RandomUtils {
	
	public static final String ALL_CHAR = "2345678ABCDEFHJKLMNPQRSTUVWXYZ";
	public static final String NUMBER_CHAR = "2345678";
	
	/** 
	 * 返回一个定长的随机字符串(只包含大写字母数字) 
	 * 
	 * @param length 随机字符串长度 
	 * @return 随机字符串 
	 */ 
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
		}
		return sb.toString();
	}
	
	/**
	 * 返回一个定长的随机字符串(只包含数字)
	 * 
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateNumberString(int length) {
		StringBuffer sb = new StringBuffer();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
		}
		return sb.toString();
	}
	
	public static String emailActiveValiDateCode(String email){
		 String code = generateString(6);
		 String valieDateCode = MD5Util.generateMD5(email, code);
		 return valieDateCode;
	}
	
	public static void main(String[] args){
		System.out.println(generateString(6));
	}
	
}

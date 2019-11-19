package cn.enjoy.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company:享学信息科技有限公司 Co., Ltd.</p>
 * @author Wangshuo
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public class MD5Util {
	public static String generateMD5(String s,String key) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
		String str = s + key;
		digest.update(str.getBytes());
		return ByteUtils.byteArray2HexString(digest.digest());
	}
	
	/**
	 * 固定加密，不带随机数
	 * @创建人 周礼
	 * @创建时间 2015年11月20日
	 * @创建目的 【】
	 * @修改目的 【修改人：，修改时间：】
	 * @param str
	 * @return
	 */
	public static String generateMD5(String str) {
	    MessageDigest digest;
	    try {
	        digest = MessageDigest.getInstance("MD5");
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException();
	    }
	    digest.update(str.getBytes());
	    return ByteUtils.byteArray2HexString(digest.digest());
	}

	/**
	 * 生成签名
	 * @创建人 Ray
	 * @创建日期 2016/5/31
	 * @param erpKey  type of String  配置在表o_biz_company_param中
	 * @param spId  type of String 公司ID
	 * @param timestamp  type of String 时间戳
	 * @return String 签名
	 */
	public static String sign(String erpKey, String spId, String timestamp){
		return MD5(erpKey + spId + timestamp);
	}

	/**
	 * 生成 MD5 --此方法是和ERP的.NET一致的加密
	 * .net 代码：
	 *   string value = "1234567";
		 string md5Str = "0123456789ABCDEF";
		 System.Security.Cryptography.MD5CryptoServiceProvider md5 = new System.Security.Cryptography.MD5CryptoServiceProvider();
		 byte[] buffer = md5.ComputeHash(Encoding.Default.GetBytes(value));
		 string strMD5Value = string.Empty;
		 for (int i = 0; i < buffer.Length; i++)
		 {
			 int a = 0xf & buffer[i] >> 4;
			 int b = buffer[i] & 0xf;
			 strMD5Value +=  md5Str.Substring(0xf & buffer[i] >> 4, 1) + md5Str[buffer[i] & 0xf];
		 }
		 MessageBox.Show(strMD5Value);
	 * 结果与 {@code generateMD5} 其实是一样的，只不过是大写
	 * @创建人 Ray
	 * @创建日期 2016/7/6
	 * @param inStr  type of String
	 * @return String
	 */
	public static String MD5(String inStr){
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
		byte[] md = digest.digest(inStr.getBytes());
		String md5 = "";
		for (int i = 0; i < md.length; i++) {
			md5+=("" + "0123456789ABCDEF".charAt(0xf & md[i] >> 4) + "0123456789ABCDEF".charAt(md[i] & 0xf));
		}
		return md5.toLowerCase();
	}

	public static void main(String[] args) {
		String erpKey = "NFERP";
		String spId = "fdc552ae66354f3caa0a68b577ad3b04";
		String timstamp = String.valueOf(System.currentTimeMillis());
		String str = erpKey + spId+ timstamp;
		System.out.println("time = " + timstamp);
		System.out.println("sign(erpKey,spId,timstamp) = " + sign(erpKey, spId, timstamp));
		System.out.println("MD5(erpKey + spId + timestamp) = " + MD5(erpKey + spId + timstamp));
		System.out.println("MD5(1234567) = " + MD5("1234567"));
		System.out.println("MD5(" + str+") = " + MD5(str));
	}
}

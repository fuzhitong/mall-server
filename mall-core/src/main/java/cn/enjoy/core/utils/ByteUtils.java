package cn.enjoy.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtils {
    private final static Logger logger = LoggerFactory.getLogger(ByteUtils.class);

    /**
     * 十六进制 转换 byte[]
     * 
     * @param hexStr
     * @return
     */
    public static byte[] hexString2ByteArray(String hexStr) {
        if (hexStr == null) return null;
        hexStr = hexStr.replaceAll(" ", "");
        if (hexStr.length() % 2 != 0) {
            return null;
        }
        byte[] data = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++ ) {
            char hc = hexStr.charAt(2 * i);
            char lc = hexStr.charAt(2 * i + 1);
            byte hb = hexChar2Byte(hc);
            byte lb = hexChar2Byte(lc);
            if (hb < 0 || lb < 0) {
                return null;
            }
            int n = hb << 4;
            data[i] = (byte)(n + lb);
        }
        return data;
    }

    public static byte hexChar2Byte(char c) {
        if (c >= '0' && c <= '9') return (byte)(c - '0');
        if (c >= 'a' && c <= 'f') return (byte)(c - 'a' + 10);
        if (c >= 'A' && c <= 'F') return (byte)(c - 'A' + 10);
        return -1;
    }

    public static String byte2HexChar(byte b) {
        String tmp = Integer.toHexString(0xFF & b);
        if (tmp.length() < 2) tmp = "0" + tmp;
        return tmp;
    }

    /**
     * byte[] 转 16进制字符串
     * 
     * @param arr
     * @return
     */
    public static String byteArray2HexString(byte[] arr) {
        StringBuilder sbd = new StringBuilder();
        for (byte b : arr) {
            String tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() < 2) tmp = "0" + tmp;
            sbd.append(tmp);
        }
        return sbd.toString();
    }

    /**
     * 空格分隔的hex string
     * 
     * @param arr
     * @return
     */
    public static String byteArray2HexStringWithSpace(byte[] arr) {
        StringBuilder sbd = new StringBuilder();
        for (byte b : arr) {
            String tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() < 2) tmp = "0" + tmp;
            sbd.append(tmp);
            sbd.append(" ");
        }
        return sbd.toString();
    }

    /**
     * 取start到end的byte array，包含end。
     * 
     * @param data
     * @param start
     * @param end
     * @return
     */
    static public byte[] getData(byte[] data, int start, int end) {
        byte[] t = new byte[end - start + 1];
        System.arraycopy(data, start, t, 0, t.length);
        return t;
    }

    /**
     * 从data取start到end的数据，返回bcd string。end包含在取值范围。
     * 
     * @param data
     * @param start
     * @param end
     * @return
     */
    static public String getBCDString(byte[] data, int start, int end) {
        byte[] t = new byte[end - start + 1];
        System.arraycopy(data, start, t, 0, t.length);
        return ByteUtils.byteArray2HexString(t);
    }

    /**
     * 从data取start到end的数据，返回hex string。end包含在取值范围。
     * 
     * @param data
     * @param start
     * @param end
     * @return
     */
    static public String getHexString(byte[] data, int start, int end) {
        byte[] t = new byte[end - start + 1];
        System.arraycopy(data, start, t, 0, t.length);
        return ByteUtils.byteArray2HexStringWithSpace(t);
    }
    
    /**
     * 根据issource，生成一个长度为4的byte数组 此数组记录isource
     * 
     * @param isource
     * @return
     */
    public static byte[] toByteArray(int isource) {
        return toByteArray(isource, 4);
    }

    /**
     * 根据issoirce，生成一个长度为len的字节数组
     * 
     * @param isource
     * @param len
     * @return
     */
    public static byte[] toByteArray(int isource, int len) {
        byte[] bl = new byte[len];
        for (int i = 0; i < len; i++) {
            bl[i] = (byte) (isource >> 8 * i & 0xff);
        }
        return bl;
    }

    /**
     * 拼接两个字符数组
     * 
     * @param a
     * @param b
     * @return
     */
    public static byte[] revert(byte[] a, byte[] b) {
        if (a == null) {
            a = new byte[0];
        }
        if (b == null) {
            b = new byte[0];
        }
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
    
    /**
     * 将byte数组转换成打印格式字符串，方便输出调试信息
     * 
     * @param data
     * @param dataLen
     * @return
     */
    public static String toPrintString(byte[] data, int dataLen) {
        if (data == null) return "";
        if (dataLen < 0) return "";
        int printLen = 0;
        if (dataLen > data.length)
            printLen = data.length;
        else
            printLen = dataLen;
        StringBuffer sb = new StringBuffer();
        String lenStr = int2HexString(data.length);
        int width = lenStr.length();
        String printStr = "";
        int loopLen = 0;
        loopLen = (printLen / 16 + 1) * 16;
        for (int i = 0; i < loopLen; i++ ) {
            if (i % 16 == 0) {
                sb.append("0x").append(formatHexStr(width, int2HexString(i))).append(": ");
                printStr = "";
            }
            if (i % 16 == 8) {
                sb.append(" ");
            }
            if (i < printLen) {
                sb.append(" ").append(formatHexStr(2, int2HexString(data[i])));
                if (data[i] > 31 && data[i] < 127)
                    printStr += (char)data[i];
                else
                    printStr += '.';
            } else {
                sb.append("   ");
            }
            if (i % 16 == 15) {
                sb.append(" ").append(printStr).append("\r\n");
            }
        }
        return sb.toString();
    }
    /**
     * 
     * @创建人 ZhouMin
     * @创建时间 2015年10月9日
     * @创建目的【int转HexString】
     * @修改目的【修改人：，修改时间：】
     * @param n
     * @return
     */
    private static String int2HexString(int n) {
        return Integer.toHexString(n).toUpperCase();
    }

    /**
     * 格式化Hex字符串的宽度，不足左补'0'
     * 
     * @param width
     * @param hexStr
     * @return
     */
    private static String formatHexStr(int width, String hexStr) {
        if (hexStr.length() >= width) {
            return hexStr.substring(hexStr.length() - width);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < width - hexStr.length(); i++ ) {
            sb.append("0");
        }
        sb.append(hexStr);
        return sb.toString();
    }
}
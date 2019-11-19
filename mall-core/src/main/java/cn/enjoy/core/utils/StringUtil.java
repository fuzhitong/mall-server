package cn.enjoy.core.utils;


import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Title
 * @Description 字符串处理类
 * @Copyright Copyright (c) 2015</p>
 * @Company 享学信息科技有限公司 Co., Ltd.</p>
 * @author ZhouMin
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public final class StringUtil {

    /**
     * 字符串右侧填充指定字符
     * 
     * @param value
     * @param totalLen 填充后长度
     * @param fillChar
     * @return
     */
    public static String rightFill(String value, int totalLen, char fillChar) {
        int fillLen = totalLen - value.length();
        if (fillLen <= 0) return value;
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        for (int i = 0; i < fillLen; i++ ) {
            sb.append(fillChar);
        }
        return sb.toString();
    }

    /**
     * 字符串左侧填充指定字符
     * 
     * @param value
     * @param totalLen
     * @param fillChar
     * @return
     */
    public static String leftFill(String value, int totalLen, char fillChar) {
        int fillLen = totalLen - value.length();
        if (fillLen <= 0) return value;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fillLen; i++ ) {
            sb.append(fillChar);
        }
        sb.append(value);
        return sb.toString();
    }

    /**
     * 2012-5-29下午4:59:39
     * 
     * @Description: 判断字符串等于空
     * @param str
     * @return boolean 返回类型
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * @创建人 ZhouMin
     * @创建时间 2015年10月9日
     * @创建目的【 判断字符串不等于空】
     * @修改目的【修改人：，修改时间：】
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str);
    }

    public static String trimNull(String s) {
        if (s == null)
            return "";
        else
            return s;
    }

    /**
     * 驼峰命名转换
     * 
     * @param name
     * @return
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
    


    /**
     * @创建人 ZhouMin
     * @创建时间 2015年10月9日
     * @创建目的【html特殊字符转换】
     * @修改目的【修改人：，修改时间：】
     * @param str
     * @return
     */
    public static String htmlspecialchars(String str) {
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot;");
        return str;
    }





    
    /**
     * 去掉小数点后多余0 
     * @创建人 lison
     * @创建时间 2015年10月27日
     * @创建目的【】
     * @修改目的【修改人：，修改时间：】
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){    
        if(s.indexOf(".") > 0){    
            s = s.replaceAll("0+?$", "");//去掉多余的0    
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉    
        }    
        return s;    
    }    

    /**
     * 返回source中匹配正则表达式regex的所有子串
     * 示例：source = "取${person}付款当日${paymentTime}的${section}"  ， 
     * 示例1：regex = "\\$\\{[^\\}]*\\}"， index = 0, 返回[${person}, ${paymentTime}, ${section}]
     * 示例2：regex = "\\$\\{([^\\}]*)\\}" ， index = 1, 返回[person, paymentTime, section]
     * @创建人：何睿
     * @创建时间：2015年12月11日
     * @创建目的：【】
     * @修改目的：【修改人：，修改时间：】
     * @param source
     * @param regex
     * @param index
     * @return
     */
    public static List<String> getSubStrAryByReg(String source,String regex, int index){
        try{
            List<String> list = new ArrayList<String>();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(source);
            while (matcher.find()) {
                list.add(matcher.group(index));
            }
            return list;
        }catch(Exception ex){
            return null;
        }
    }
    public static List<String> getSubStrAryByReg(String source,String regex){
        return getSubStrAryByReg(source,regex,0);
    }

    /**
     * 将source 中的变量替换为map中的值
     * @创建人 何睿
     * @创建时间 2016年4月21日
     * @param source 源字符串
     * @param regex 正则
     * @param params 参数
     * @return String
     */
    public static String replace(String source, String regex, Map<String, String> params){
        if(params == null || params.isEmpty()){
            return source;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);
        while (m.find()) {
            String key = m.group(1);
            Object value = params.get(key);
            if(value != null){
                source = source.replace(m.group(0), params.get(key));
            }
        }
        return source;
    }

    public static int parseInt(Object o){
        if(o == null){
            return 0;
        }else{
            return Integer.parseInt(o.toString());
        }
    }

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "ray");
        params.put("password", "pswd");
        String source = "服务$商${name}已经${password}了您编${name}${name}号为${contractNo}的签约申请";

        String out = replace(source, "\\$\\{([^\\$]*)}", params);
        System.out.println("out = " + out);
    }

    private static Pattern p = Pattern.compile("\\s{2}|\t|\r|\n");
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     * @param str 字符串
     * @return 长度
     */
    public static int getLengthb(String str){
        if (str == null)
            return 0;
        char[] c = str.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5
     * @param  s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static double getLength(String s) {
        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                // 其他字符长度为0.5
                valueLength += 0.5;
            }
        }
        //进位取整
        return  Math.ceil(valueLength);
    }

    public static boolean isMultiBytesChar(char c) throws UnsupportedEncodingException {
        // 如果字节数大于1，是汉字
        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了
        return String.valueOf(c).getBytes("UTF-8").length > 1;
    }



    /**
     * 获取对象的值为空值的对象
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || srcValue.toString() == "") emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}

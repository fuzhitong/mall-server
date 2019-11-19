package cn.enjoy.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	/**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";

    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 精确到分钟
     */
    public static String FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd日";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 中文全称 如：2010年12月01日 23时15分
     */
    public static String FORMAT_MEDIUM_CN = "yyyy年MM月dd日 HH时mm分";

    public static String FORMAT_NOYEAR_CN = "MM月dd日 HH:mm";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";
    
    /**
     * erp专用格式
     */
    private static String FORMAT_ERP = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * erp专用格式(只有时间部分)
     */
    public static String FORMAT_ERP_TIME = "HH:mm";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    /**
     * 根据预设格式返回当前日期
     * 
     * @return
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     * 
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }
    
    /**
     * 得到当天日期格式化字符串
     * 
     * @return
     */
    public static final String getTodayStr() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(now);
    }
    
    /**
     * 使用预设格式格式化日期
     * 
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     * 
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate
     *            日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }

    /**
     * 使用用户格式提取字符串日期
     * 
     * @param strDate
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        if(StringUtil.isEmpty(strDate)){
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在日期上增加数个整月
     * 
     * @param date
     *            日期
     * @param n
     *            要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     * 
     * @param date
     *            日期
     * @param n
     *            要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     * 
     * @param date
     *            日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     * 
     * @param date
     *            日期字符串
     * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     * 
     * @param date
     *            日期字符串
     * @param format
     *            日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 用于计算短信验证码的失效时间
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int countMinutes(Date beginTime, Date endTime){
        Calendar begin = Calendar.getInstance();
        begin.setTime(parse(format(beginTime),FORMAT_MINUTE));
        Calendar end = Calendar.getInstance();
        end.setTime(parse(format(endTime),FORMAT_MINUTE));
        long beginLong = begin.getTimeInMillis();
        long endLong = end.getTimeInMillis();
        return (int)((endLong / 1000) - (beginLong / 1000))/60;
    }

//    public static void main(String[] args) {
//        Date begin = parse("2016-09-28 10:00:59");
//        Date end = parse("2016-09-28 10:05:45");
//        System.out.println(countMinutes(begin,end));
//    }
    public static boolean isToday(Date date) {
        Calendar c1 = Calendar.getInstance();              
        c1.setTime(date);                                 
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);   
        
        Calendar c2 = Calendar.getInstance();    
        c2.setTime(new Date());      
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH)+1;
        int day2 = c2.get(Calendar.DAY_OF_MONTH);   
        
        if(year1 == year2 && month1 == month2 && day1 == day2){
            return true;
        }
        return false;                               
    }         
    
    /**
     * 返回加或减后的日期
     * 
     * @param date
     *            要格式化的日期
     * @param field
     *            the calendar field
     * @param amount
     *            the amount of date or time to be added to the field
     * @return
     */
    public static final Date add(Date date,int field,int amount) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        Date retDate = calendar.getTime();
        return retDate;
    }
    
    public static Calendar addMinutes(Date date, int minuteNum) {
        if(date == null){
            date = new Date();
        }
        Calendar c = Calendar.getInstance();              
        c.setTime(date);                                 
        c.add(Calendar.MINUTE, minuteNum);                    
        return c;                               
    }   
    
    public static String getNextDate(String oldDate, int sizeOfParamValue) {
        try {
            if ((oldDate.length() != 8)) {
                System.out.println("oldDate 必须是YYYYMMDD格式");
                return null;
            }
            int year = Integer.parseInt(oldDate.substring(0, 4));
            int month = Integer.parseInt(oldDate.substring(4, 6)) - 1;
            int date = Integer.parseInt(oldDate.substring(6, 8));

            Calendar calender = Calendar.getInstance();
            calender.set(year, month, date);
            calender.add(Calendar.DATE, sizeOfParamValue);

            year = calender.get(Calendar.YEAR);
            month = calender.get(Calendar.MONTH) + 1;
            date = calender.get(Calendar.DATE);

            String NextDate = StringUtil.leftFill(new StringBuffer().append(year)
                    .toString(), 4, '0')
                    + StringUtil.leftFill(new StringBuffer().append(month).toString(), 2,
                            '0')
                    + StringUtil.leftFill(new StringBuffer().append(date).toString(), 2,
                            '0');

            return NextDate;
        } catch (Exception e) {
            System.out.println("发生异常!");
            return null;
        }
    }
    
    /***
     * 比对当前时间
     * <li>创建人：xiaopu</li>
     * <li>创建时间：2015年9月22日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param date1
     * @return
     */
    public static boolean getCompareDate(Date date1,int number){

    	 Calendar c1= Calendar.getInstance();

    	 Calendar c2= Calendar.getInstance();

    	 c1.setTime(date1);
    	 if(number!=0){
    		 c1.add(Calendar.HOUR,number);//把日期往后增加N小时.整数往后推,负数往前移动
//    		  date=calendar.getTime();   //这个时间就是日期往后推一天的结果
    	 }
    	 int result=c1.compareTo(c2);

    	 if(result==0){

    	 System.out.println("c1相等c2");
    	 return true;

    	 }else if(result<0){

    		 System.out.println("c1小于c2");
    		 return false;
    	 }else{

    		 System.out.println("c1大于c2");
    		 return true;
    	 }
    }

    /**
     * ERP日期时间字符串转换成Date
     */
    public static Date toErpDate(String strDate){
        return parse(strDate,FORMAT_ERP);
    }

    /**
     * DATE转换成ERP日期时间字符串
     */
    public static String toErpString(Date date){
        return format(date,FORMAT_ERP);
    }

    /**
     * DATE转换成ERP时间字符串
     */
    public static String toErpStringTime(Date date){
    	return format(date,FORMAT_ERP_TIME);
    }


    /**
     *
     * @param s1	开始日期
     * @param s2	结束日期
     * @return	2 格式有错，0相等，1大于 -1小于
     */
    public static int compare(String s1,String s2){
    	//String s1="2008-01-25 09:12:09";
    	//String s2="2008-01-29 09:12:11";
    	java.text.DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Calendar c1= Calendar.getInstance();
    	Calendar c2= Calendar.getInstance();
    	try
    	{
    	c1.setTime(df.parse(s1));
    	c2.setTime(df.parse(s2));
    	}catch(ParseException e){
    		//System.err.println("格式不正确");
    		return 2;
    	}
    	int result=c1.compareTo(c2);
    	if(result==0)
    		//System.out.println("s1相等s2");
    		return 0;
    	else if(result<0)
    		//System.out.println("s1小于s2");
    		return -1;
    	else
//    		System.out.println("s1大于s2");
    		return 1;
    }

    /**
     * 计算两个日期间的天数
     * @创建人 Ray
     * @创建日期 2016/12/29
     *
     * @param checkIn  type of String
     * @param checkOut  type of String
     * @return int
     */
    public static int getBetweenDays(String checkIn,String checkOut){
        long between_days = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date smdate= null;
        Date bdate= null;
        try {
            smdate = sdf.parse(checkIn);
            bdate = sdf.parse(checkOut);

            smdate=sdf.parse(sdf.format(smdate));
            bdate=sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            between_days=(time2-time1)/(1000*3600*24);
        } catch (ParseException e) {
            logger.error("计算两个日期间天数出错", e);
        }
        return (int)between_days;

    }

    /**
     * 取得星期几
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    /**
     * 转换成UNIX TIMESTAMP 单位：秒
     * @param dateStr
     * @param format
     * @return
     */
    public static long date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String getDateWeekByTimeStamp(long timeStamp){
        String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Date date = new Date(timeStamp);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int week = instance.get(Calendar.DAY_OF_WEEK) - 1;
        if(week < 0){
            week = 0;
        }
        return weekOfDays[week];
    }
    public static void main(String[] args) {
//        System.out.println(getWeekOfDate(new Date()));

        System.out.println(getDateWeekByTimeStamp(System.currentTimeMillis()));
//		System.out.println(compare("2008-01-25 09:12:09","2008-01-25 09:12:09"));
//		System.out.println(compare("2009-01-25 09:12:09","2008-01-25 09:12:09"));
//		System.out.println(compare("2008-01-25 09:12:09","2009-01-25 09:12:09"));
//		System.out.println(compare("2008-01-25 09:12:09","200a-01-25 09:12:09"));
//        String TimeStamp = DateUtil.format(new Date(), DateUtil.FORMAT_LONG);
//        System.out.println(format(new Date(),FORMAT_LONG));
//        System.out.println(getTimeString());
    }
}

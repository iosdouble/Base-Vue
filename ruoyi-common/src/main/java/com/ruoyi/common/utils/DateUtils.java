package com.ruoyi.common.utils;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ruoyi.common.constant.DateFormatConstants;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 * 
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }
    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd

     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    /**
     * 获取当前日期，默认格式为 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 按照传入的格式返回时间字符串
     *
     * @param formate 传入时间格式
     * @return
     */
    public static String getDate(String formate){
        return dateTimeNow(formate);
    }


    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 开始时间与结束时间比较
     * @param startDate
     * @param end
     * @return
     */
    public static int differentDays(Date startDate ,Date end)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(end);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //不同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //同一年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }


    /**
     * 按照字符串时间格式计算时间差
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String smdate,String bdate){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long time1 = cal.getTimeInMillis();
        try {
            cal.setTime(sdf.parse(smdate));
            cal.setTime(sdf.parse(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取指定时间戳
     * @param dateStr
     * @return
     */
    public static long getMillis(String dateStr){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            return sdf.parse(dateStr).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 当前时间往后或者往前推迟 指定的分钟，其中
     * mimute为正数表示当前时间往后多少分钟，为负数表示当前时间往前多少分钟　
     *
     * @param minute
     * @return
     * @throws Exception
     */
    public static String getAnyMinute(int minute) throws Exception {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cale = Calendar.getInstance();  //获取实例化的日历对象
        cale.add(Calendar.MINUTE, minute);  //cale.get(Calendar.MINUTE) 获取当前分钟数
        return sdFormat.format(cale.getTime());
    }

    /**
     * 将日期字符串解析成Date 格式：yyyy-MM-dd HH:mm:ss
     * @param dateStr 指定的字符串
     */
    public static Date convertTimeStrToDate(String dateStr) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.parse(dateStr);
    }

    /**
     * 某个时间点的下个月的第一天
     * @param day
     * @return
     */
    public static Date fristDayInNextMonth(Date day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)+1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    /**
     * 获取当前系统时间的小时(HH)
     *
     * @return int 当前系统小时HH格式
     * @throws Exception
     */
    public static int getHH() throws Exception {
        DateFormat df = new SimpleDateFormat("HH");
        Date date = new Date(System.currentTimeMillis());
        String str = df.format(date);
        return Integer.parseInt(str);
    }

    /**
     * 获取当前系统时间的分钟数(mm)
     *
     * @return int 当前系统分钟mm格式
     * @throws Exception
     */
    public static int getMM() throws Exception {
        DateFormat df = new SimpleDateFormat("mm");
        Date date = new Date(System.currentTimeMillis());
        String str = df.format(date);
        // return Integer.parseInt(str.split(":")[0]) * 4 +
        // Integer.parseInt(str.split(":")[1]) / 15;
        return Integer.parseInt(str);
    }

    /**
     * 获取当前系统时间的秒数(ss)
     *
     * @return int 当前系统时间的秒数(ss)
     * @throws Exception
     */
    public static int getSS() throws Exception {
        DateFormat df = new SimpleDateFormat("ss");
        Date date = new Date(System.currentTimeMillis());
        String str = df.format(date);
        // return Integer.parseInt(str.split(":")[0]) * 4 +
        // Integer.parseInt(str.split(":")[1]) / 15;
        return Integer.parseInt(str);
    }

    /**
     * 获取输入日期的前后日期
     *
     * @param date
     *      基准日期 yyyy-MM-dd
     * @param dayMark
     *      +代表往后,-代表往前
     * @return String 前后日期（yyyy-MM-dd）
     * @throws Exception
     */
    public static String getOtherDay(String date, int dayMark) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = df.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DAY_OF_MONTH, dayMark);
        String mDateTime = df.format(c.getTime());
        String strStart = mDateTime.substring(0, 10);
        return strStart;
    }

    /**
     * 获取日期所在周的第一天(周一)
     *
     * @return String 周一（yyyy-MM-dd）
     * @throws Exception
     *
     * */
    public static String getWeekFirstDate(String date) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = df.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int days = c.get(Calendar.DAY_OF_WEEK);
        String strStart = "";
        if (days == 1) {
            strStart = getOtherDay(date, -days - 5);
        } else {
            strStart = getOtherDay(date, -days + 2);
        }
        return strStart;
    }


    /**
     * 获取日期所在周的最后一天（周日）
     *
     * @param date 基准日期 yyyy -MM-dd
     * @return String（yyyy-MM-dd）
     * @throws Exception
     *
     * */
    public static String getWeekLastDate(String date) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = df.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int days = c.get(Calendar.DAY_OF_WEEK);
        String strStart = "";
        if (days == 1) {
            strStart = getOtherDay(date, 0);
        } else {
            strStart = getOtherDay(date, 8 - days);
        }
        return strStart;
    }

    /**
     * 获取日期所在月的第一天 date基准日期
     *
     * @param date
     *      yyyy-MM-dd
     * @return String （yyyy-MM）
     * @throws Exception
     *
     * */
    public static String getMonthFirstDate(String date) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = df.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        c.set(year, month, 1);
        String mDateTime = df.format(c.getTime());
        String strStart = mDateTime.substring(0, 10);
        return strStart;
    }

    /**
     * 获取日期所在月的最后一天
     *
     * @param date 基准日期 yyyy-MM-dd
     * @return String yyyy-MM-dd
     * @throws Exception
     *
     * */
    public static String getMonthLastDate(String date) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = df.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayNum = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(year, month, dayNum);
        String mDateTime = df.format(c.getTime());
        String strStart = mDateTime.substring(0, 10);
        return strStart;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param sDate
     *      -- 起始日期yyyy-MM-dd
     * @param eDate
     *      -- 结束日期yyyy-MM-dd
     * @return int--天数
     * @throws Exception
     * */
    public static int getDaysOfTwoDate(String sDate, String eDate)
            throws Exception {

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = df1.parse(sDate);
        Date date2 = df2.parse(eDate);
        if (null == date1 || null == date2) {
            return -1;
        }
        long intervalMilli = date2.getTime() - date1.getTime();
        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }

    /**
     * 获取两个月份之间的月数
     *
     * @param sDate
     *      -- 起始月yyyy-MM
     * @param eDate
     *      -- 结束月yyyy-MM
     * @return int--天数
     * @throws Exception
     * */
    public static int getMonthOfTwoMonth(String sDate, String eDate)
            throws Exception {

        DateFormat df = new SimpleDateFormat("yyyy-MM");

        Date date1 = df.parse(sDate);
        Date date2 = df.parse(eDate);
        if (null == date1 || null == date2) {
            return -1;
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        int month1 = c1.get(Calendar.YEAR) * 12 + c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.YEAR) * 12 + c2.get(Calendar.MONTH);

        return month2 - month1;
    }

    /**比较两个日期
     * @param sDate 起始日期
     * @param eDate 结束日期
     * @param pattern 日期格式
     * @return boolean 返回比较结果
     * @throws Exception
     */
    public static boolean compareDate(String sDate, String eDate, String pattern)
            throws Exception {

        DateFormat df1 = new SimpleDateFormat(pattern);
        Date date1 = df1.parse(sDate);
        Date date2 = df1.parse(eDate);
        if (null == date1 || null == date2) {
            return false;
        }
        long intervalMilli = date2.getTime() - date1.getTime();
        if (intervalMilli > 0) {
            return true;
        }
        return false;
    }

    /**获取两个日期之间的分钟数
     * @param sDate 起始日期 yyyy-MM-dd HH:mm:ss
     * @param eDate 结束日期 yyyy-MM-dd HH:mm:ss
     * @return int--分钟数
     * @throws Exception
     */
    public static int getMinsOfTwoDate(String sDate, String eDate)
            throws Exception {

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = df1.parse(sDate);
        Date date2 = df2.parse(eDate);
        if (null == date1 || null == date2) {
            return -1;
        }
        long intervalMilli = date2.getTime() - date1.getTime();
        return (int) (intervalMilli / (60 * 1000));
    }

    /**
     * 解析字符串为Date类型
     * @param date 要被解析的日期字符串
     * @param pattern 类型格式，默认yyyy-MM-dd HH:mm:ss
     * @return Date 被解析后的日期
     * @throws Exception
     */
    public static Date parseDate(String date, String pattern) throws Exception {
        Date returnDate = null;
        if (pattern == null || pattern.equals("") || pattern.equals("null")) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(pattern);
        try {
            returnDate = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnDate;
    }
    /**
     * 格式化Date类型日期
     * @param date Date类型日期
     * @param pattern 类型格式
     * @return String，被格式化后的日期
     * @throws Exception
     */
    public static String formatDate(Date date, String pattern) throws Exception {
        String returnDate = null;

        if (date == null) {
            return "";
        }

        if (pattern == null || pattern.equals("") || pattern.equals("null")) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(pattern);
        returnDate = sdf.format(date);

        return returnDate;
    }

    /**
     * 根据年月字符串得到那个月的总天数
     * @param monthStr yyyy-MM
     * @return int 总天数
     * @throws Exception
     */
    public static int getDaysOfMonth(String monthStr) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = format.parse(monthStr);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int dayNum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return dayNum;
    }

    /**
     * 获取指定时刻前后指定步长的时刻
     *
     * @param time
     *      时刻HH:mm:ss
     * @param m
     *      步长(+-m) 0:输出原来的时刻，+1输出后一个时刻，-1输出前一个时刻
     * @return String HH:mm:ss
     * @throws Exception
     */
    public static String getBeforeAfterTimeForMin(String time, int m)
            throws Exception {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date newDate = new Date();
        newDate = format.parse(time);
        now.setTime(newDate);
        now.add(Calendar.MINUTE, m);
        return format.format(now.getTime());
    }

    /**
     * 获取指定格式的前后时间
     *
     * @param time
     * @param m
     *      (0:输出原来的时刻，+1输出后一个时刻，-1输出前一个时刻)
     * @param pattern
     *      类型的格式必须被time的格式所包含
     * @return
     * @throws Exception
     */
    public static String getBeforeAfterDateTimeByTime(String time, int m, String pattern) throws Exception {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date newDate = new Date();
        newDate = sdf.parse(time);
        now.setTime(newDate);
        now.add(Calendar.MINUTE, m);

        return sdf.format(now.getTime());
    }
}

package com.dg11185.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 说明：日期操作工具类
 *
 * @author xdweleven
 * @version 1.0
 * @since 2016-09-18
 * @Copyright 2016 全国邮政电子商务运营中心 All rights reserved.
 */
public class DateUtil {

    private static SimpleDateFormat getSdf(final String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 日期型转为字符串
     *
     * @param date
     * @param pattern  匹配
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } else {
            return "";
        }

    }

    public static String date2Str(Date date, String pattern) {
        if (date != null)
            return getSdf(pattern).format(date);
        else
            return "";
    }

    /**
     * 字符串转为日期类型
     *
     * @param dateStr
     * @return
     */
    public static Date formateStrToDate(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            return null;
        }

    }

    public static Date str2Date(String date, final String pattern) {
        try {
            return getSdf(pattern).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 得到当前日期
     *
     * @param pattern
     * @return
     */
    public static String getNowDate(String pattern) {
        return formatDate(new Date(), pattern);
    }

    public static String now(String pattern) {
        return date2Str(new Date(), pattern);
    }

    /**
     * 增加年份
     *
     * @param amount
     * @return
     */
    public static Date addYear(int amount) {
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.YEAR, amount);
        return cd.getTime();
    }

    /**
     * 增加年份
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addYear(Date date,int amount) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.YEAR, amount);
        return cd.getTime();
    }

    /**
     * 增加月份
     *
     * @param amount
     * @return
     */
    public static Date addMonth(int amount) {
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.MONTH, amount);
        return cd.getTime();
    }

    /**
     * 增加月份
     *
     * @param amount
     * @return
     */
    public static Date addMonth(Date date,int amount) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.MONTH, amount);
        return cd.getTime();
    }

    /**
     * 增加天数
     *
     * @param amount
     * @return
     */
    public static Date addDay(int amount) {
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.DAY_OF_MONTH, amount);
        return cd.getTime();
    }

    /**
     * 增加天数
     *
     * @param amount
     * @return
     */
    public static Date addDay(Date date,int amount) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DAY_OF_MONTH, amount);
        return cd.getTime();
    }

    /**
     * 增加小时数
     *
     * @param amount
     * @return
     */
    public static Date addHour(Date date,int amount) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.HOUR_OF_DAY, amount);
        return cd.getTime();
    }

    /**
     * 增加分钟数
     *
     * @param amount
     * @return
     */
    public static Date addMinute(Date date,int amount) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.MINUTE, amount);
        return cd.getTime();
    }

    /**
     * 增加毫秒数
     *
     * @param amount
     * @return
     */
    public static Date addSecond(Date date,int amount) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.SECOND, amount);
        return cd.getTime();
    }

    /**
     * 得到毫秒数
     *
     * @return
     */
    public static Long getMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 得到小时数
     * @return
     */
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 得到年份
     *
     * @return
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 得到月份
     *
     * @return
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * 得到天数
     *
     * @return
     */
    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 两个日期的天数差
     *
     * @param fromDate
     * @param endDate
     * @return
     */
    public static int diffDay(Date fromDate, Date endDate) {
        long diff = endDate.getTime() - fromDate.getTime();
        return (int) (diff / (24 * 3600 * 1000));
    }

    /**
     * 两个日期的天数差
     *
     * @param fromDate
     * @param endDate
     * @return
     */
    public static long diffMillis(Date fromDate, Date endDate) {
        return endDate.getTime() - fromDate.getTime();
    }

    /**
     * 在指定日期上加上指定的天数
     *
     * @param nowDate 指定日期
     * @param day  加上的天数
     * @return 返回加上天数后的日期
     * @throws ParseException
     */
    public static Date addDate(Date nowDate, long day) throws ParseException {
        long time = nowDate.getTime();
        day = day * 24 * 60 * 60 * 1000;
        time += day;
        return new Date(time);
    }




    //add by caiweining 2016-12-01 begin
    /*
     * 获取当前日期的指定格式
     */
    public static Date getCurrentFormatTime(){
    	Date currentTime = null;
    	try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDateString = format.format(new Date());
			currentTime = format.parse(currentDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return currentTime;

    }
    //add by caiweining 2016-12-01 end
    
    /**
     * 获取当前日期的前M天
      * @return
      * @return Date
      * @author:caiweining
      * @date:2017年7月12日
     */
    public static Date getMdaysago(Integer mDays){
    	
    	Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。   
    	cal.add(Calendar.DAY_OF_MONTH, mDays);//取当前日期的前一天.
    	return  formateStrToDate(formatDate(cal.getTime(), "yyyy-MM-dd"),"yyyy-MM-dd");
    }

    public static void main(String[] args){
//        Date nowDate = formateStrToDate("2010-02-08", "yyyy-MM-dd");
//        Date tempDate = formateStrToDate("2010-02-08", "yyyy-MM-dd");
//        System.out.println(nowDate.before(tempDate));
        System.out.println(formatDate(getMdaysago(-1),"yyyy-MM-dd"));

    }
 
}

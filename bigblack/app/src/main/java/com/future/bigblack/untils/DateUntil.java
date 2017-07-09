package com.future.bigblack.untils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kevin on 2017/7/4.
 */

public class DateUntil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

    //获得当天0点时间
    public static int getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    //获得当天24点时间
    public static int getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    public static String getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return simpleDateFormat1.format(c.getTime());
    }


    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return simpleDateFormat1.format(c.getTime());
    }

    /**
     * 将时间转换为时间戳
     *
     * @param s 2017-1-1
     * @return
     * @throws ParseException
     */
    public static String dateToStamp(String s) throws ParseException {
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /**
     * 将时间戳转换为时间
     *
     * @param s 2017-01-01
     * @return
     */
    public static String stampToDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        return simpleDateFormat.format(date);
    }

    /**
     * @return 2017-01-11
     */
    public static String getCurrentYMD() {
        Date d = new Date();
        return simpleDateFormat1.format(d);
    }
}

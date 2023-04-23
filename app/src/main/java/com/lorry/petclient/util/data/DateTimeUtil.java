package com.lorry.petclient.util.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {


    public static String format(String create_date, String create_time) {
        String pattern = "yyyy-MM-dd";
        String curDate = getCurDate(pattern);
        long cur_dateStamp = getStringToDate(curDate, pattern);
        long old_dateStamp = getStringToDate(create_date, pattern);
        if (cur_dateStamp == old_dateStamp) {
            String curTime = getCurDate("HH-mm-ss");
            long cur_timeStamp = getStringToDate(curTime, pattern);
            long old_timeStamp = getStringToDate(create_time, pattern);
            int second = (int) (old_timeStamp - cur_timeStamp) / (1000);
            if (second <= 60) return "刚刚";
            if (second <= 3600) return String.format("%d分钟前", second * 60);
            return String.format("%d小时前", second * 3600);
        } else {
            int days = (int) (cur_dateStamp - old_dateStamp) / (1000 * 3600 * 24);
            if (days <= 30) return getDateToString(old_dateStamp, "dd") + "天前";
            if (days <= 365) return getDateToString(old_dateStamp, "MM-dd");
            return getDateToString(old_dateStamp, "yyyy-MM-dd");
        }
    }

    public static long getCurTimeLong() {
        long time = System.currentTimeMillis();
        return time;
    }

    public static String getCurDate(String pattern) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new java.util.Date());
    }

    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static long getStringToDate(String dateString, String pattern) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

}

package com.our.smart.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {


    //获取目标时间和现在时间差几年
    public static String getYearDifference(String targetTime, String pattern) {
        int yearDifference = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date targetDate = sdf.parse(targetTime);
            Date nowDate = new Date();
            long diff = nowDate.getTime() - targetDate.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long years = days / 365;
            yearDifference = (int) years;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return yearDifference + "年";
    }

}

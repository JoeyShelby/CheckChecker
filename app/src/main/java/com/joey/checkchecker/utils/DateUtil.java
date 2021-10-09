package com.joey.checkchecker.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对日期经行格式化等操作
 */
public class DateUtil {
    /**
     * 日期格式化
     * @param date  传入需要进行格式化的日期对象
     * @return 返回格式化好的日期字符串
     */
    public static String formatDate(Date date){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        return simpleDateFormat.format(date);

    }

    public static Date stringToDate(String string){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("com.joey.checkchecker.utils.DateUtil.stringToDate:", "error");
        }
        return null;
    }


}

package com.ypf.myapp.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ypf on 2016/1/11.
 */
public class TimeUtil {
    //y-年 M-月 m-分钟 d-天 H-24制 h-12制 s-秒
    public static final String pattern1 = "yyyy-MM-dd";

    public static String getCurrentTime(String pattern)
    {
        if (TextUtils.isEmpty(pattern))
            pattern = pattern1;
        Date now=new Date();
        return getFormatDate(now, pattern);
    }

    public static String getFormatDate(Date date, String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}

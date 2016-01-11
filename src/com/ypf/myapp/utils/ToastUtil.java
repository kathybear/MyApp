package com.ypf.myapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ypf on 2016/1/11.
 */
public class ToastUtil {
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    public static void shortToast(Context context, String str){
        show(context, str, LENGTH_SHORT);
    }

    public static void shortToast(Context context, int str){
        show(context, str, LENGTH_SHORT);
    }

    public static void longToast(Context context, String str){
        show(context, str, LENGTH_LONG);
    }

    public static void longToast(Context context, int str){
        show(context, str, LENGTH_LONG);
    }

    public static void show(Context context, String str, int duration){
        Toast.makeText(context, str, duration).show();
    }

    public static void show(Context context, int str, int duration){
        Toast.makeText(context, str, duration).show();
    }
}

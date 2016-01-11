package com.ypf.myapp.utils;

import android.content.Context;
import android.content.Intent;
import com.ypf.myapp.activity.MainActivity;
import com.ypf.myapp.activity.MyRoundDotActivity;
import com.ypf.myapp.activity.MyRunningDotsActivity;
import com.ypf.myapp.activity.ViewPagerAndFragmentActivity;

/**
 * Created by ypf on 2016/1/11.
 */
public class Intents {
    public static void skipMainActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void skipMyRoundDotActivity(Context context){
        Intent intent = new Intent(context, MyRoundDotActivity.class);
        context.startActivity(intent);
    }

    public static void skipMyRunningDotsActivity(Context context){
        Intent intent = new Intent(context, MyRunningDotsActivity.class);
        context.startActivity(intent);
    }

    public static void skipViewPagerAndFragmentActivity(Context context){
        Intent intent = new Intent(context, ViewPagerAndFragmentActivity.class);
        context.startActivity(intent);
    }
}

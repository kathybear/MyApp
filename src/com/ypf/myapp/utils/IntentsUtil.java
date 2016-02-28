package com.ypf.myapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.ypf.myapp.activity.*;

/**
 * Created by ypf on 2016/1/11.
 */
public class IntentsUtil {
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

    public static void skipFlowLayoutActivity(Context context){
        Intent intent = new Intent(context, FlowLayoutActivity.class);
        context.startActivity(intent);
    }

    public static void skipUncaughtExceptionActivity(Context context){
        Intent intent = new Intent(context, UncaughtExceptionActivity.class);
        context.startActivity(intent);
    }

    public static void skipTabActivity(Context context){
        Intent intent = new Intent(context, TabActivity.class);
        context.startActivity(intent);
    }

    public static void skipPhonePicForResult(Context context, int requestCode){
        Intent getAlbum = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity) context).startActivityForResult(getAlbum, requestCode);
    }

    public static void skipTelPhone(Context context, String phoneCode){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneCode));
        context.startActivity(intent);
    }

    public static void skipWebViewActivity(Context context){
        Intent intent = new Intent(context, WebViewActivity.class);
        context.startActivity(intent);
    }

    public static void skipChartViewActivity(Context context){
        Intent intent = new Intent(context, ChartViewActivity.class);
        context.startActivity(intent);
    }

    public static void skipDateShowActivity(Context context){
        Intent intent = new Intent(context, DateShowActivity.class);
        context.startActivity(intent);
    }

    public static void skipViewPagerAndFragmentActivity(Context context){
        Intent intent = new Intent(context, ViewPagerAndFragmentActivity.class);
        context.startActivity(intent);
    }
}

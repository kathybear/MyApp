package com.ypf.myapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by ypf on 2015/12/23.
 */
public class MyRoundDotActivity extends Activity {
    private MyRoundDot mydot;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrounddot);
        context = this;
//        activity跳转时动画，在startActivity或finish后使用
//        overridePendingTransition();
        mydot = (MyRoundDot) findViewById(R.id.mydot);
    }
}
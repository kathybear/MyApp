package com.ypf.myapp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.ypf.myapp.R;
import com.ypf.myapp.view.MyRoundDot;

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
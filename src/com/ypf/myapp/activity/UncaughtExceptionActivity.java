package com.ypf.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import com.ypf.myapp.R;

/**
 * Created by ypf on 2016/1/15.
 */
public class UncaughtExceptionActivity extends Activity {
    private String s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncaughtexception);
        System.out.println(s.equals("any string"));
    }
}
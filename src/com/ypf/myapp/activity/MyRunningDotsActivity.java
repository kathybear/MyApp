package com.ypf.myapp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.ypf.myapp.R;
import com.ypf.myapp.view.RunningDots;

/**
 * Created by ypf on 2016/1/8.
 */
public class MyRunningDotsActivity extends Activity {
    private Context context;
    private RunningDots runningDots;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrunningdots);
        context = this;

        runningDots = (RunningDots) findViewById(R.id.runningDots);
        runningDots.setDefaultStart(true);
    }
}
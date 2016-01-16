package com.ypf.myapp.common;

import android.app.Application;
import com.ypf.myapp.tools.CrashHandler;

/**
 * Created by ypf on 2016/1/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /*CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());*/
    }
}

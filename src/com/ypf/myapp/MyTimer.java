package com.ypf.myapp;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ypf on 2016/1/8.
 */
public class MyTimer {
    private Handler handler;
    private Timer timer;
    private MyTask mTask;

    public MyTimer(Handler handler) {
        super();
        this.handler = handler;
        timer = new Timer();
    }

    public void schedule(long period){
        cancel();
        mTask = new MyTask(handler);
        timer.schedule(mTask, 0, period);
    }

    public void cancel(){
        if(null != mTask){
            mTask.cancel();
            mTask = null;
        }
    }

    class MyTask extends TimerTask {
        private Handler handler;

        public MyTask(Handler handler) {
            super();
            this.handler = handler;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            handler.obtainMessage().sendToTarget();
        }
    }
}

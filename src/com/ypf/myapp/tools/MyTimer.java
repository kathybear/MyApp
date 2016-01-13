package com.ypf.myapp.tools;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ypf on 2016/1/8.
 */
public class MyTimer {
    private Handler handler;
    private Timer timer;
    private MyTask mTask;
    private boolean isNeedWhat = false;
    private int what;
    private Message msg = null;
    private long delayMillis = 0;

    public MyTimer(Handler handler) {
        super();
        this.handler = handler;
        timer = new Timer();
    }

    public MyTimer(Handler handler, boolean isNeedWhat, int what) {
        this(handler);
        this.isNeedWhat = isNeedWhat;
        this.what = what;
    }

    public MyTimer(Handler handler, boolean isNeedWhat, int what, long delayMillis) {
        this(handler, isNeedWhat, what);
        this.delayMillis = delayMillis;
    }

    public MyTimer(Handler handler, boolean isNeedWhat, int what, Message msg) {
        this(handler, isNeedWhat, what);
        this.msg = msg;
    }

    public MyTimer(Handler handler, boolean isNeedWhat, int what, long delayMillis, Message msg) {
        this(handler, isNeedWhat, what, delayMillis);
        this.msg = msg;
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
            if (null != msg){
                if (delayMillis > 0)
                    handler.sendMessageDelayed(msg, delayMillis);
                else
                    handler.sendMessage(msg);
            } else if (isNeedWhat){
                if (delayMillis > 0)
                    handler.sendEmptyMessageDelayed(what, delayMillis);
                else
                    handler.sendEmptyMessage(what);
            } else {
                handler.obtainMessage().sendToTarget();
            }
        }
    }
}

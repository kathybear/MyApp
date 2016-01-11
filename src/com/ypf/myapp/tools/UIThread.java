package com.ypf.myapp.tools;

import android.os.Handler;
import android.os.Message;
import com.ypf.myapp.view.PullAndLoadLayout;

/**
 * Created by ypf on 2015/12/18.
 */
public class UIThread extends Thread{
    private Handler handler = null;
    private Message msg = null;
    private int state;

    public UIThread(Handler handler, int state) {
        this.handler = handler;
        this.state = state;
    }

    @Override
    public void run() {
        switch (state){
            case PullAndLoadLayout.REFRESHING:
                msg = handler.obtainMessage();
                msg.what = state;
                handler.sendMessageDelayed(msg, 5000);
                break;
            case PullAndLoadLayout.LOADING:
                msg = handler.obtainMessage();
                msg.what = state;
                handler.sendMessageDelayed(msg, 5000);
                break;
        }
    }
}

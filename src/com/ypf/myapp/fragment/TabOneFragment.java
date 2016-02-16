package com.ypf.myapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.ypf.myapp.R;
import com.ypf.myapp.tools.ChooseCity;

/**
 * Created by ypf on 2016/1/21.
 */
public class TabOneFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private LinearLayout linear_search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_tabone, container, false);
        linear_search = (LinearLayout) parent.findViewById(R.id.linear_search);
        linear_search.setOnClickListener(this);

        return parent;
    }

    @Override
    public void onResume() {
        super.onResume();
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //16-2-16  在进入后就退出，会导致延时事件在触发后找不到绑定的视图，然后报错
                if (!activity.isFinishing())
                    linear_search.performClick();
            }
        };
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_search:
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                ChooseCity cc = new ChooseCity(activity, location[1] + v.getMeasuredHeight());
                cc.init();
                cc.show(v);
                break;
        }
    }
}
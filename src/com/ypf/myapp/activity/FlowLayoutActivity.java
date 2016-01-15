package com.ypf.myapp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.ypf.myapp.R;
import com.ypf.myapp.view.FlowLayout;

/**
 * Created by ypf on 2016/1/15.
 */
public class FlowLayoutActivity extends Activity implements View.OnClickListener {
    private Context context;
    private FlowLayout flowLayout;
    private int textNum = 30;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);
        context = this;

        flowLayout = (FlowLayout) findViewById(R.id.flowLayout);
        for (int i = 0; i < textNum; i++){
            TextView text = (TextView) LayoutInflater.from(context).inflate(R.layout.textview_flowlayout, null);
            text.setText("MyApp="+i);
            text.setOnClickListener(this);
            flowLayout.addView(text);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}
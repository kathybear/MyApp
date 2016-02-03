package com.ypf.myapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ypf.myapp.R;
import com.ypf.myapp.fragment.TabOneFragment;

/**
 * Created by ypf on 2016/1/21.
 */
public class TabActivity extends FragmentActivity {
    private Context context;
    private LinearLayout linear_tab;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        context = this;

        fm = getSupportFragmentManager();

        linear_tab = (LinearLayout) findViewById(R.id.linear_tab);
        for (int i = 0; i < 4; i++){
            TextView text = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_tab, linear_tab, false);
            text.setText("text"+i);
            linear_tab.addView(text);
        }
        linear_tab.getChildAt(0).setSelected(true);
        findViewById(R.id.image).setSelected(true);
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_content, new TabOneFragment());
        ft.commit();
    }
}
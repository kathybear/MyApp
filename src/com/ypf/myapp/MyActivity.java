package com.ypf.myapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.ypf.myapp.utils.Intents;

import java.util.List;

public class MyActivity extends Activity {
    private ListView list_main;
    private Context context;
    private List<String> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;

        list_main = (ListView) findViewById(R.id.list_main);
        String[] strs = getResources().getStringArray(R.array.first_directory);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                Intents.skipMainActivity(context);
                break;
            case R.id.btn_dot:
                Intents.skipMyRoundDotActivity(context);
                break;
            case R.id.btn_running_dots:
                Intents.skipMyRunningDotsActivity(context);
                break;
        }
    }
}

package com.ypf.myapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.ypf.myapp.adapter.MyAdapter;
import com.ypf.myapp.utils.IntentsUtil;

import java.util.ArrayList;
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

        init();
    }

    private void init(){
        list_main = (ListView) findViewById(R.id.list_main);
        String[] strs = getResources().getStringArray(R.array.first_directory);
        items = new ArrayList<String>();
        for (String str : strs)
            items.add(str);
        list_main.setAdapter(new MyAdapter(items, context, R.layout.list_item_myactiivity));
        list_main.setOnItemClickListener(listMainOnItemClickListener);
    }

    private AdapterView.OnItemClickListener listMainOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    IntentsUtil.skipMainActivity(context);
                    break;
                case 1:
                    IntentsUtil.skipMyRoundDotActivity(context);
                    break;
                case 2:
                    IntentsUtil.skipMyRunningDotsActivity(context);
                    break;
                case 3:
                    IntentsUtil.skipFlowLayoutActivity(context);
                    break;
                case 4:
                    IntentsUtil.skipUncaughtExceptionActivity(context);
                    break;
                case 5:
                    IntentsUtil.skipTabActivity(context);
                    break;
                case 6:
                    IntentsUtil.skipWebViewActivity(context);
                    break;
            }
        }
    };
}

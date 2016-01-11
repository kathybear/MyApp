package com.ypf.myapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.ypf.myapp.utils.Intents;

public class MyActivity extends Activity implements View.OnClickListener{
    private Button btn;
    private Context context;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);
        findViewById(R.id.btn_running_dots).setOnClickListener(this);
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

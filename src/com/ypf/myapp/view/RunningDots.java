package com.ypf.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.ypf.myapp.R;
import com.ypf.myapp.bean.MyRunningDots;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/11.
 */
public class RunningDots extends View {
    private List<MyRunningDots> dots = new ArrayList<MyRunningDots>();
    private Paint mPaint;
    private int gray;
    private boolean first = false;
    private final int DOTNUM = 4;
    private final int DOTDISTANCE = 20;

    public RunningDots(Context context) {
        super(context);
        init();
    }

    public RunningDots(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RunningDots(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gray = getResources().getColor(R.color.gray);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!first){
            first = true;
            float dotY = getMeasuredHeight() / 2f;
            MyRunningDots dot;
            for (int i = 0; i < DOTNUM; i++){
                dot = new MyRunningDots(DOTDISTANCE, dotY, i);
                dots.add(dot);
            }
        }
        super.onDraw(canvas);
    }
}

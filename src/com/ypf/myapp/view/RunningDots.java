package com.ypf.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.ypf.myapp.R;
import com.ypf.myapp.bean.MyRunningDots;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/11.
 */
public class RunningDots extends View {
    public enum AnimType{
        REVERSE, SEQUENCE
    }

    private final int DOTNUM = 4;
    private final int DOTDISTANCE = 20;
    private final int RUNNING = 0;

    private List<MyRunningDots> dots = new ArrayList<MyRunningDots>();
    private Paint mPaint;
    private int color;
    private boolean first = true;
    private float radius = 10;
    private float computeHeight = 100;
    private int averageNum = 10;
    private float averageAngle;
    private int timeDiff = 50;
    private int halfWidth;
    private boolean isReverse = false;
    private AnimType type = AnimType.SEQUENCE;
    private boolean isDefaultStart = false;
    private boolean isRunning = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case RUNNING:
                    computeDots();
                    break;
            }
        }
    };

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
        color = getResources().getColor(R.color.red);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (first){
            first = false;
            float dotY = getMeasuredHeight() / 2f;
            float endX = getMeasuredWidth() - DOTDISTANCE;
            halfWidth = getMeasuredWidth() / 2;
            averageAngle = (float) (Math.toDegrees(Math.atan((halfWidth - DOTDISTANCE) / computeHeight))) / averageNum;
            MyRunningDots dot;
            for (int i = 0; i < DOTNUM; i++){
                dot = new MyRunningDots(DOTDISTANCE, endX, DOTDISTANCE, dotY, averageNum + i, radius, color);
                dots.add(dot);
            }
            if (isDefaultStart)
                startAnim();
        }
        MyRunningDots dot;
        for (int i = 0; i < DOTNUM; i++){
            dot = dots.get(i);
            if (dot.getIndex() <= averageNum) {
                mPaint.setColor(dot.getColor());
                canvas.drawCircle(dot.getCurX(), dot.getCurY(), dot.getRadius(), mPaint);
            }
        }
        super.onDraw(canvas);
    }

    private void computeDots(){
        MyRunningDots dot = null;
        float distance;
        for (int i = 0; i < DOTNUM; i++){
            dot = dots.get(i);
            if (isReverse) {
                if (type == AnimType.REVERSE)
                    dot.setIndex(dot.getIndex() + 1);
            } else
                dot.setIndex(dot.getIndex() - 1);
            if (dot.getIndex() <= averageNum || dot.getIndex() >= -averageNum) {
                distance = (float) (computeHeight * Math.tan(Math.toRadians(averageAngle * dot.getIndex())));
                dot.setCurX(halfWidth - distance);
            }
        }
        if (dot.getIndex() <= -averageNum) {
            if (type == AnimType.REVERSE)
                isReverse = true;
            else if (type == AnimType.SEQUENCE) {
                for (int i = 0; i < DOTNUM; i++) {
                    dot = dots.get(i);
                    dot.setIndex(averageNum + i);
                }
            }
        } else if (dot.getIndex() >= averageNum + DOTNUM - 1)
            isReverse = false;

        postInvalidate();
        handler.sendEmptyMessageDelayed(RUNNING, timeDiff);
    }

    public void setAverageNum(int averageNum) {
        this.averageNum = averageNum;
    }

    public void setType(AnimType type) {
        this.type = type;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void startAnim(){
        isRunning = true;
        handler.sendEmptyMessageDelayed(RUNNING, timeDiff);
    }

    public void stopAnim(){
        isRunning = false;
        handler.removeMessages(RUNNING);
    }

    public void setDefaultStart(boolean defaultStart) {
        isDefaultStart = defaultStart;
    }

    public boolean isRunning() {
        return isRunning;
    }
}

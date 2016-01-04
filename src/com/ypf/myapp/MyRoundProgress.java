package com.ypf.myapp;

import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ypf on 2015/12/19.
 */
public class MyRoundProgress extends View{
    private Paint mPaint;
    private RectF ovalBottom = null;
    private float bottomX;
    private float bottomY;
    private float bottomRadius;
    private int lightBlue;
    private int lightGray;
    private int progressWidth = 4;
    private int progressColor = Color.BLACK;
    private float progress = 0f;
    private float totalProgress;
    private boolean isStartAnim = false;
    private List<Dot> dots = new ArrayList<Dot>();
    private final int DOTDISTANCE = 20;
    private final int DOTNUM = 4;
    private float fromX = DOTDISTANCE;
    private float toX = DOTDISTANCE;
    private float dotY;
    private float dotAngle;
    private MyTimer time;

    public MyRoundProgress(Context context) {
        super(context);
        init();
    }

    public MyRoundProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRoundProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        lightBlue = getResources().getColor(R.color.light_blue);
        lightGray = getResources().getColor(R.color.light_gray);

        time = new MyTimer(animHandler);
    }

    @Override
    public void draw(Canvas canvas) {
        if (isStartAnim){
            time.schedule(50);
        }else{
            mPaint.setStrokeWidth(getProgressWidth() * 2 + 2);
            mPaint.setColor(lightGray);
            canvas.drawCircle(bottomX, bottomY, bottomRadius, mPaint);
            if (progress > getTotalProgress()){
                if (null != ovalBottom) {
                    mPaint.setStrokeWidth(getProgressWidth() - 2);
                    mPaint.setColor(lightBlue);
                    canvas.drawCircle(bottomX, bottomY, bottomRadius, mPaint);
                    mPaint.setStrokeWidth(getProgressWidth());
                    mPaint.setColor(Color.BLACK);
                    canvas.drawArc(ovalBottom, 90, (getProgress() - getTotalProgress()) / getTotalProgress() * 360, false, mPaint);
                }
            } else {
                if (null != ovalBottom) {
                    mPaint.setStrokeWidth(getProgressWidth() - 2);
                    mPaint.setColor(Color.BLACK);
                    canvas.drawCircle(bottomX, bottomY, bottomRadius, mPaint);
                    mPaint.setStrokeWidth(getProgressWidth());
                    mPaint.setColor(lightBlue);
                    canvas.drawArc(ovalBottom, 90, getProgress() / getTotalProgress() * 360, false, mPaint);
                }
            }
        }
        super.draw(canvas);
    }

    private void drawRegion(Canvas canvas,Region rgn)
    {
        RegionIterator iter = new RegionIterator(rgn);
        Rect r = new Rect();

        while (iter.next(r)) {
            canvas.drawRect(r, mPaint);
        }
    }

    public int getProgressWidth() {
        return progressWidth;
    }

    public void setProgressWidth(int progressWidth) {
        this.progressWidth = progressWidth;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        bottomRadius = progress / 6f - getProgressWidth() / 2f;
        bottomX = getMeasuredWidth() / 2f;
        bottomY = getMeasuredHeight() - progress / 2f;
        ovalBottom = new RectF(bottomX - bottomRadius, bottomY - bottomRadius, bottomX + bottomRadius, bottomY + bottomRadius);

        this.progress = progress;
        postInvalidate();
    }

    public float getTotalProgress() {
        return totalProgress;
    }

    public void setTotalProgress(float totalProgress) {
        this.totalProgress = totalProgress;
    }

    public boolean isStartAnim() {
        return isStartAnim;
    }

    public void setStartAnim(boolean startAnim) {
        isStartAnim = startAnim;
        if (startAnim){
            toX = getMeasuredWidth() - fromX;
            dotY = getMeasuredHeight() - getTotalProgress() / 2f;
            dotAngle = (float) (Math.atan((bottomX - fromX) / (getTotalProgress() / 2f)) / Math.PI * 180);
            dots.clear();
            Dot dot;
            for (int i = 0; i < DOTNUM; i++){
                dot = new Dot(fromX, dotY, i);
                dots.add(dot);
            }
        }
    }

    class Dot{
        private float curX;
        private float curY;
        private int index;

        public Dot(float curX, float curY, int index) {
            this.curX = curX;
            this.curY = curY;
            this.index = index;
        }

        public float getCurX() {
            return curX;
        }

        public void setCurX(float curX) {
            this.curX = curX;
        }

        public float getCurY() {
            return curY;
        }

        public void setCurY(float curY) {
            this.curY = curY;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    private Handler animHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            for (int i = 0; i < DOTNUM; i++){

            }
        }
    };

    class MyTimer{
        private Handler handler;
        private Timer timer;
        private MyTask mTask;

        public MyTimer(Handler handler) {
            super();
            this.handler = handler;
            timer = new Timer();
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
                handler.obtainMessage().sendToTarget();
            }
        }
    }
}

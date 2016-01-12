package com.ypf.myapp.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import com.ypf.myapp.R;

/**
 * Created by ypf on 2015/12/19.
 */
public class MyRoundProgress extends View{
    public enum Direction{
        TOP, BOTTOM, LEFT, RIGHT
    }

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
    private Direction direction = Direction.BOTTOM;

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
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint.setStrokeWidth(getProgressWidth() * 2 + 2);
        mPaint.setColor(lightGray);
        canvas.drawCircle(bottomX, bottomY, bottomRadius, mPaint);
        int startAngle = 90;
        if (direction == Direction.TOP)
            startAngle = -90;
        else if (direction == Direction.LEFT)
            startAngle = 180;
        else if (direction == Direction.RIGHT)
            startAngle = 0;
        if (progress > getTotalProgress()){
            if (null != ovalBottom) {
                mPaint.setStrokeWidth(getProgressWidth() - 2);
                mPaint.setColor(lightBlue);
                canvas.drawCircle(bottomX, bottomY, bottomRadius, mPaint);
                mPaint.setStrokeWidth(getProgressWidth());
                mPaint.setColor(Color.BLACK);
                canvas.drawArc(ovalBottom, startAngle, (getProgress() - getTotalProgress()) / getTotalProgress() * 360, false, mPaint);
            }
        } else {
            if (null != ovalBottom) {
                mPaint.setStrokeWidth(getProgressWidth() - 2);
                mPaint.setColor(Color.BLACK);
                canvas.drawCircle(bottomX, bottomY, bottomRadius, mPaint);
                mPaint.setStrokeWidth(getProgressWidth());
                mPaint.setColor(lightBlue);
                canvas.drawArc(ovalBottom, startAngle, getProgress() / getTotalProgress() * 360, false, mPaint);
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
        if (direction == Direction.BOTTOM){
            bottomX = getMeasuredWidth() / 2f;
            bottomY = getMeasuredHeight() - progress / 2f;
        } else if (direction == Direction.TOP){
            bottomX = getMeasuredWidth() / 2f;
            bottomY = progress / 2f;
        } else if (direction == Direction.LEFT){
            bottomX = getMeasuredWidth() - progress / 2f;
            bottomY = getMeasuredHeight() / 2f;
        } else if (direction == Direction.RIGHT){
            bottomX = progress / 2f;
            bottomY = getMeasuredHeight() / 2f;
        }

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

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}

package com.ypf.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;
import com.ypf.myapp.R;
import com.ypf.myapp.tools.MyTimer;

/**
 * Created by ypf on 2016/1/13.
 */
public class GrayTextView extends TextView {
    private final int CONTINUE = 0;
    private final float INITRADIUS = 80;

    private float downX;
    private float downY;
    private MyTimer timer;
    private Paint mPaint;
    private int color;
    private int timeDiff = 10;
    private boolean isShowAnim = false;
    private float radius = INITRADIUS;
    private float averageAddRadius = 20;
    private int viewWidth;
    private boolean isFirst = true;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CONTINUE:
                    computeCircleRadius();
                    break;
            }
        }
    };

    public GrayTextView(Context context) {
        super(context);
        init();
    }

    public GrayTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GrayTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        timer = new MyTimer(handler, true, CONTINUE);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        color = getResources().getColor(R.color.gray);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!isShowAnim){
                    isShowAnim = true;
                    downX = event.getX();
                    downY = event.getY();
                    timer.schedule(timeDiff);
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isFirst){
            viewWidth = getMeasuredWidth();
            isFirst = false;
            mPaint.setColor(color);
        }

        if (isShowAnim){
            canvas.drawCircle(downX, downY, radius, mPaint);
        }
        super.onDraw(canvas);
    }

    private void computeCircleRadius(){
        if (isContinue()){
            radius += averageAddRadius;
            postInvalidate();
        } else {
            isShowAnim = false;
            radius = INITRADIUS;
            timer.cancel();
            postInvalidate();
        }
    }

    private boolean isContinue(){
        if (downX < viewWidth / 2 && downX + radius > viewWidth)
            return false;
        else if (downX >= viewWidth / 2 && downX - radius < 0)
            return false;
        else
            return true;
    }
}

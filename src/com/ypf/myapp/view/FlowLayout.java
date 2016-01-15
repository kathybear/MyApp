package com.ypf.myapp.view;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by ypf on 2016/1/15.
 */
public class FlowLayout extends RelativeLayout {
    private final int START = -1;
    private final int CONTINUE = 0;
    private final int ANIMNUM = 5;
    private final int END = 1;

    private boolean isFirst = true;
    private boolean isAnim = false;
    private int animNum;
    private int layoutWidth;
    private int layoutHeight;
    private int down;
    private int timeDiff = 30;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CONTINUE:
                    requestLayout();
                    break;
            }
        }
    };

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (isFirst){
            isFirst = false;
            layoutWidth = getMeasuredWidth();
            layoutHeight = getMeasuredHeight();
        }
        if (isAnim) {
            childLayout(down);
        }
        else {
            animNum = END;
            childLayout(START);
        }
    }

    private void childLayout(int from){
        Point curSize;
        if (from == START)
            curSize = new Point(0, 0);
        else
            curSize = new Point(getChildAt(from).getLeft(), getChildAt(from).getTop());
        int childNum = getChildCount();
        View child;
        for (int i = from + 1; i < childNum; i++){
            child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (childWidth + curSize.x <= layoutWidth){
            } else {
                curSize.set(0, curSize.y + childHeight);
            }
            int curX;
            int curY;
            if (animNum == END) {
                curX = curSize.x;
                curY = curSize.y;
            } else {
                curX = (int) (child.getLeft() - (child.getLeft() - curSize.x) * 1f / animNum);
                curY = (int) (child.getTop() - (child.getTop() - curSize.y) * 1f / animNum);
            }
            child.layout(curX, curY, curX + childWidth, curY + childHeight);
            curSize.offset(childWidth, 0);

        }
//        Log.e("childLayout", "animNum=" + animNum);
        if (from > START) {
            if (animNum == END) {
                isAnim = false;
                removeViewAt(from);
            } else {
                animNum--;
                deleteView();
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!isAnim) {
                    float downX = ev.getX();
                    float downY = ev.getY();
                    down = pointToPosition(downX, downY);
                    Log.e("dispatchTouchEvent", "down=" + down);
                    if (down > -1) {
                        isAnim = true;
                        animNum = ANIMNUM;
                        deleteView();
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void deleteView(){
        handler.sendEmptyMessageDelayed(CONTINUE, timeDiff);
    }

    private int pointToPosition(float x, float y){
        int index = -1;
        int childNum = getChildCount();
        View child;
        for (int i = 0; i < childNum; i++) {
            child = getChildAt(i);
            if (x >= child.getLeft() && x <= child.getRight() && y >= child.getTop() && y <= child.getBottom()){
                index = i;
                break;
            }
        }
        return index;
    }
}

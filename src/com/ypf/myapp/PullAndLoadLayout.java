package com.ypf.myapp;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ypf on 2015/12/18.
 */
public class PullAndLoadLayout extends RelativeLayout {
    //初始状态，处于下拉刷新或上拉加载
    public static final int INIT = 0;
    //释放刷新
    public static final int RELEASE_TO_REFRESH = 1;
    //正在刷新
    public static final int REFRESHING = 2;
    //释放加载
    public static final int RELEASE_TO_LOAD = 3;
    //正在加载
    public static final int LOADING = 4;
    //完成，刷新完毕或加载完毕
    public static final int DONE = 5;
    //当前状态
    private int state = INIT;
    // 下拉头
    private View headView;
    private MyRoundProgress headmrp;
    //上拉头
    private View footView;
    // 拉动内容
    private AbsListView contentView;
    private boolean firstLayout = true;
    // 释放刷新的距离
    private float refreshDist = 200;
    // 释放加载的距离
    private float loadDist = 200;
    // 下拉的距离
    private float pullDownDist = 0;
    // 上拉的距离
    private float pullUpDist = 0;
    // 是否可以下拉
    private boolean canPullDown = false;
    // 是否可以上拉
    private boolean canPullUp = false;
    //上个事件的点坐标
    private float lastY = 0;
    // 手指滑动距离与下拉头的滑动距离比，中间会随正切函数变化
    private float radio = 2;

    private MyTimer timer;
    //回滚速度
    private float rollBackSpeed = 8;
    //	//刷新或加载过程中滑动
    //	private boolean isTouch = false;

    public PullAndLoadLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    public PullAndLoadLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }

    public PullAndLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        timer = new MyTimer(hideHandler);
    }


    //通过layout()达到下拉或上拉的效果
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        if(firstLayout){
            firstLayout = false;
            headView = getChildAt(0);
            headmrp = (MyRoundProgress) headView.findViewById(R.id.mrp1);
            contentView = (AbsListView) getChildAt(1);
            refreshDist = ((ViewGroup) headView).getChildAt(1).getMeasuredHeight();
            headmrp.setTotalProgress(refreshDist);

            footView = getChildAt(2);
            loadDist = ((ViewGroup) footView).getChildAt(0).getMeasuredHeight();
        }
        //pullDownDist和pullUpDist不会同时不为0
        headView.layout(0, (int) pullDownDist + (int) pullUpDist - headView.getMeasuredHeight(), headView.getMeasuredWidth(), (int) pullDownDist + (int) pullUpDist);
        contentView.layout(0, (int) pullDownDist + (int) pullUpDist, contentView.getMeasuredWidth(), (int) pullDownDist + contentView.getMeasuredHeight() + (int) pullUpDist);
        footView.layout(0, contentView.getMeasuredHeight() + (int) pullDownDist + (int) pullUpDist, footView.getMeasuredWidth(), contentView.getMeasuredHeight() + footView.getMeasuredHeight() + (int) pullDownDist + (int) pullUpDist);
    }

    private void hide(){
        timer.schedule(5);
    }

    private Handler hideHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if(canPullDown){
                rollBackSpeed = (float) (8 + 5 * Math.tan(Math.PI / 2 /getMeasuredHeight() * pullDownDist));
                if(state == REFRESHING && pullDownDist <= refreshDist){
                    pullDownDist = refreshDist;
                    headmrp.setStartAnim(true);
                    timer.cancel();
                } else {
                    pullDownDist -= rollBackSpeed;
                    if(pullDownDist <= 0){
                        pullDownDist = 0;
                        timer.cancel();
                        if(state == DONE)
                            changeState(INIT);
                    }
                }

                requestLayout();
                headmrp.setProgress(pullDownDist);
            }
            if (canPullUp){
                rollBackSpeed = (float) (8 + 5 * Math.tan(Math.PI / 2 /getMeasuredHeight() * Math.abs(pullUpDist)));
                if (state == LOADING && pullUpDist >= -loadDist){
                    pullUpDist = -loadDist;
                    timer.cancel();
                } else {
                    pullUpDist += rollBackSpeed;
                    if (pullUpDist >= 0){
                        pullUpDist = 0;
                        timer.cancel();
                        if (state == DONE)
                            changeState(INIT);
                    }
                }

                requestLayout();
            }
        }

    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        switch(ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getY();
                timer.cancel();

                break;
            case MotionEvent.ACTION_MOVE:
                isCanPullDown();
                isCanPullUp();
                // 可以下拉，正在加载时不能下拉
                if(pullDownDist > 0 || canPullDown && !canPullUp && state != LOADING){
                    // 对实际滑动距离做缩小，造成用力拉的感觉
                    pullDownDist += (ev.getY() - lastY) / radio;
                    //当head完全隐藏后，防止能继续上滑，隐藏AbsListView
                    if(pullDownDist < 0)
                        pullDownDist = 0;
                    //不让下拉的长度超过控件的长度
                    if(pullDownDist > getMeasuredHeight())
                        pullDownDist = getMeasuredHeight();
                    //				if(state == REFRESHING)
                    //					isTouch = true;
                    lastY = ev.getY();
                    // 根据下拉距离改变比例，以到达对实际滑动距离做缩小，造成用力拉的感觉
                    radio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight() * pullDownDist));
                    //通过重新布局达到滑动效果
                    requestLayout();

                    headmrp.setProgress(pullDownDist);

                    if(pullDownDist >= refreshDist && state == INIT)
                        changeState(RELEASE_TO_REFRESH);
                    if(pullDownDist <= refreshDist && state == RELEASE_TO_REFRESH)
                        changeState(INIT);
                    //取消点击事件和长按点击事件，而不用反射弧
                    if(pullDownDist > 0)
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                }

                if(pullUpDist < 0 || canPullUp && !canPullDown && state != REFRESHING){
                    pullUpDist += (ev.getY() - lastY) / radio;
                    if (pullUpDist > 0)
                        pullUpDist = 0;
                    if (pullUpDist < -getMeasuredHeight())
                        pullUpDist = -getMeasuredHeight();
                    lastY = ev.getY();
                    radio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight() * (-pullUpDist)));
                    requestLayout();
                    if (pullUpDist <= -loadDist && state == INIT)
                        changeState(RELEASE_TO_LOAD);
                    if (pullUpDist >= -loadDist && state == RELEASE_TO_LOAD)
                        changeState(INIT);
                    if (pullUpDist < 0)
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(state == RELEASE_TO_REFRESH){
                    changeState(REFRESHING);
                    if(null != listener)
                        listener.pullDown();
                }
                if(state == RELEASE_TO_LOAD){
                    changeState(LOADING);
                    if(null != listener)
                        listener.pullUp();
                }
                hide();
                break;
        }
        super.dispatchTouchEvent(ev);
        return true;
    }

    private void isCanPullDown(){
        if(contentView.getChildCount() == 0)
            canPullDown = true;
        else if(contentView.getFirstVisiblePosition() == 0 && contentView.getChildAt(0).getTop() >= 0)
            canPullDown = true;
        else
            canPullDown = false;
    }

    private void isCanPullUp(){
        if(contentView.getLastVisiblePosition() == contentView.getCount() - 1){
            View end = contentView.getChildAt(contentView.getLastVisiblePosition() - contentView.getFirstVisiblePosition());
            View start = contentView.getChildAt(0);
            if(null != end && end.getBottom() <= contentView.getMeasuredHeight() && (end.getBottom() - start.getTop()) > contentView.getMeasuredHeight())
                canPullUp = true;
            else
                canPullUp = false;
        } else
            canPullUp = false;
    }

    private void changeState(int to){
        state = to;
        switch(state){
            case INIT:
                break;
            case RELEASE_TO_REFRESH:
                break;
            case REFRESHING:
                break;
            case RELEASE_TO_LOAD:
                break;
            case LOADING:
                break;
            case DONE:
                break;
        }
    }

    public void loadFinish(int loadResult){
        state = DONE;
        hide();

    }

    public void refreshFinish(int refreshResult){
        state = DONE;
        hide();

    }

    private PullAndLoadListener listener = null;

    public void setPullAndLoadListener(PullAndLoadListener listener){
        this.listener = listener;
    }

    public interface PullAndLoadListener{
        public void pullDown();
        public void pullUp();
    }
}

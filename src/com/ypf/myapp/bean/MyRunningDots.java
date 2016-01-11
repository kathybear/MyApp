package com.ypf.myapp.bean;

/**
 * Created by ypf on 2016/1/8.
 */
public class MyRunningDots {
    private float curX;
    private float curY;
    private int index;

    public MyRunningDots(float curX, float curY, int index) {
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

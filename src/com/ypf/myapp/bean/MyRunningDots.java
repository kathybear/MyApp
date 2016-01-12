package com.ypf.myapp.bean;

/**
 * Created by ypf on 2016/1/8.
 */
public class MyRunningDots {
    private float startX;
    private float endX;
    private float curX;
    private float curY;
    private int index;
    private float radius;
    private int color;

    public MyRunningDots(float startX, float endX, float curX, float curY, int index, float radius, int color) {
        this(curX, curY, index, radius, color);
        this.startX = startX;
        this.endX = endX;
    }

    public MyRunningDots(float curX, float curY, int index, float radius, int color) {
        this(curX, curY, index, radius);
        this.color = color;
    }

    public MyRunningDots(float curX, float curY, int index, float radius) {
        this(curX, curY, index);
        this.radius = radius;
    }

    public MyRunningDots(float curX, float curY, int index) {
        this.curX = curX;
        this.curY = curY;
        this.index = index;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
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

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

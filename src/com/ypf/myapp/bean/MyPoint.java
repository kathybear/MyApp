package com.ypf.myapp.bean;

/**
 * Created by ypf on 2015/12/23.
 */
public class MyPoint {
    private double X;
    private double Y;

    public MyPoint(double x, double y) {
        X = x;
        Y = y;
    }

    public void offset(double x, double y){
        X += x;
        Y += y;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }
}

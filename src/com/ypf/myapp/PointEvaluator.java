package com.ypf.myapp;

import android.animation.TypeEvaluator;

/**
 * Created by ypf on 2015/12/28.
 */
public class PointEvaluator implements TypeEvaluator<MyPoint> {
    @Override
    public MyPoint evaluate(float fraction, MyPoint startValue, MyPoint endValue) {
        double x = fraction * (endValue.getX() - startValue.getX());
        double y = fraction * (endValue.getY() - startValue.getY());
        MyPoint point = new MyPoint(startValue.getX() + x, startValue.getY() + y);
        return point;
    }
}

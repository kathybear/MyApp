package com.ypf.myapp.view;

import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.ypf.myapp.bean.MyPoint;

/**
 * Created by ypf on 2015/12/23.
 */
public class MyRoundDot extends View{
    private Paint mPaint;
    private RectF ovalBottom = null;
    private double bottomX;
    private double bottomY;
    private double bottomRadius;
    private RectF ovalTop = null;
    private double topX;
    private double topY;
    private double topRadius;
    private RectF ovalLeft = null;
    private double leftX;
    private double leftY;
    private double leftRadius;
    private RectF ovalRight = null;
    private double rightX;
    private double rightY;
    private double rightRadius;
    private float startAngle;
    private float sweepAngle;
    private Path mPath = null;
    private boolean isDot = false;
    private boolean isPull = false;
    private boolean isBreak = false;
    private MyPoint lastPoint;
    private double mathE;
    private double mathX;
//    private double mathY;
    private double breakDistance;
    private final int BACKNUM = 3;
    private final int BREAKDISTANCE = 4;
    private final int AUTOBACKTIMEDIFF = 50;
    private final int AUTOBACKFLAG = 0;
    private int backNum = BACKNUM;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AUTOBACKFLAG:
                    autoBack();
                    break;
            }
        }
    };

    public MyRoundDot(Context context) {
        super(context);
        init();
    }

    public MyRoundDot(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRoundDot(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null == ovalBottom){
            topX = getMeasuredWidth() / 2;
            topY = getMeasuredHeight() / 2;
            ovalBottom = new RectF((float) (topX - bottomRadius), (float) (topY - bottomRadius), (float) (topX + bottomRadius), (float) (topY + bottomRadius));
            ovalTop = new RectF((float) (topX - topRadius), (float) (topY - topRadius), (float) (topX + topRadius), (float) (topY + topRadius));

            mPath = new Path();
            mPath.addArc(ovalBottom, 0, 360);
        }
        canvas.drawPath(mPath, mPaint);
        super.onDraw(canvas);
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.RED);

        bottomRadius = 20;
        topRadius = 15;
        leftRadius = 100;
        rightRadius = 100;

        breakDistance = Math.sqrt(Math.pow(topRadius + leftRadius, 2) - Math.pow(leftRadius + BREAKDISTANCE / 2, 2)) +
                Math.sqrt(Math.pow(bottomRadius + leftRadius, 2) - Math.pow(leftRadius + BREAKDISTANCE / 2, 2));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
//            case MotionEvent.ACTION_SCROLL:
//                Log.e("ACTION_SCROLL", "X="+event.getX()+"--Y="+event.getY());
//                break;
            case MotionEvent.ACTION_DOWN:
                lastPoint = new MyPoint(event.getX(), event.getY());
                isPressDot();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDot){
                    float distanceX = (float) (event.getX() - lastPoint.getX());
                    float distanceY = (float) (event.getY() - lastPoint.getY());
                    ovalBottom.offset(distanceX, distanceY);
                    bottomX = ovalBottom.centerX();
                    bottomY = ovalBottom.centerY();
                    lastPoint.offset(distanceX, distanceY);

                    changeShape();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isPull && !isBreak){
                    autoBack();
                }
                if (isBreak){
                    leftX = leftY = rightX = rightY = 0;
                    topX = bottomX;
                    topY = bottomY;
                    ovalTop = new RectF((float) (topX - topRadius), (float) (topY - topRadius), (float) (topX + topRadius), (float) (topY + topRadius));
                }
                break;
        }
        super.dispatchTouchEvent(event);
        return true;
    }

    private void autoBack(){
        if (backNum > 0){
            bottomX = topX + (topX - bottomX) / 2;
            bottomY = topY + (topY - bottomY) / 2;
            ovalBottom = new RectF((float) (bottomX - bottomRadius), (float) (bottomY - bottomRadius), (float) (bottomX + bottomRadius), (float) (bottomY + bottomRadius));
            changeShape();
            handler.sendEmptyMessageDelayed(AUTOBACKFLAG, AUTOBACKTIMEDIFF);
            backNum--;
        }else{
            bottomX = topX;
            bottomY = topY;
            ovalBottom = new RectF((float) (bottomX - bottomRadius), (float) (bottomY - bottomRadius), (float) (bottomX + bottomRadius), (float) (bottomY + bottomRadius));
            mPath.reset();
            mPath.addArc(ovalBottom, 0, 360);
            postInvalidate();
            backNum = BACKNUM;
            isPull = false;
        }

    }

    private void isPressDot(){
        if (ovalBottom.contains((float) lastPoint.getX(), (float) lastPoint.getY()))
            isDot = true;
        else
            isDot = false;
    }

    private void isBreak(){
        if (mathE > breakDistance)
            isBreak = true;
        else
            isBreak = false;
    }

    private void  changeShape(){
        mPath.reset();
        //当外圆不能覆盖内圆时，开始显示内圆及切边，切边由左右圆计算
        if (!ovalBottom.contains(ovalTop)){

            //外圆圆心和内圆圆心的距离
            mathE = Math.sqrt(Math.pow(bottomX - topX, 2) + Math.pow(bottomY - topY, 2));
            //XY用于计算弧度开始角和弧度角
            //当左圆心位于E上的垂点不在E上时，X为负
            mathX = (2 * topRadius * leftRadius + Math.pow(topRadius, 2) + Math.pow(mathE, 2) - Math.pow(bottomRadius, 2) - 2 * bottomRadius * leftRadius) / (2 * mathE);
//            mathY = Math.sqrt(Math.pow(topRadius + leftRadius, 2) - Math.pow(mathX, 2));
            //Log.e("math", "mathX-->"+mathX+"--mathY-->"+mathY);
            //开始角跟随X的正负变化，不影响整体的计算方式和逻辑
            startAngle = (float) (Math.asin(mathX / (topRadius + leftRadius)) / Math.PI * 180);
            sweepAngle = (float) (Math.asin((mathE - mathX) / (bottomRadius + leftRadius)) / Math.PI * 180 + startAngle);

            //Log.e("angle", "startAngle-->"+startAngle+"--sweep-->"+sweepAngle);
            //t是外圆圆心到内圆圆心的夹角的余角
            //t在二、三象限是负，一、四象限是正
            double t = Math.asin((bottomX - topX) / mathE) / Math.PI * 180;
            //startAngle - t 是左圆圆心到内圆圆心的夹角
            //取负是计算的需要   例如：左圆圆心和外圆圆心都在第一象限，
            //此时startAngle - t是正，得到的反余弦和反正弦都为正，但是计算坐标需要的正余弦应该是负
            //完整理解需要画图，及在四个象限的各两种情况
            double tan;
            double tan1;
            //当左圆圆心在内圆圆心下方时，左圆圆心到内圆圆心的夹角已大于180，而上面的计算结果是减去了180的
            if (bottomY > topY) {
                tan = -Math.toRadians(startAngle + t + 180);
                tan1 = -Math.toRadians(360 - startAngle + t);
            }else{
                tan = -Math.toRadians(startAngle - t);
                tan1 = -Math.toRadians(180 - startAngle - t);
            }
            //通过0~360的夹角的正反余弦计算距离进而得到坐标
            double n = (topRadius + leftRadius) * Math.cos(tan);
            double m = (topRadius + leftRadius) * Math.sin(tan);
            leftX = topX + n;
            leftY = topY + m;
            double n1 = (topRadius + leftRadius) * Math.cos(tan1);
            double m1 = (topRadius + leftRadius) * Math.sin(tan1);
            rightX = topX + n1;
            rightY = topY + m1;

            isBreak();
            if (isBreak){
                isPull = false;
                mPath.addArc(ovalBottom, 0, 360);
            }else {
                isPull = true;
                ovalLeft = new RectF((float) (leftX - leftRadius), (float) (leftY - leftRadius), (float) (leftX + leftRadius), (float) (leftY + leftRadius));
                ovalRight = new RectF((float) (rightX - rightRadius), (float) (rightY - rightRadius), (float) (rightX + rightRadius), (float) (rightY + rightRadius));
                //弧度开始角也是一样，最好是画四象限共8种图
                if (bottomY > topY) {
                    mPath.addArc(ovalBottom, 180 + (float) (sweepAngle - startAngle - t), -(180 + 2 * (sweepAngle - startAngle)));
                    mPath.arcTo(ovalRight, 180 + (float) -(sweepAngle - startAngle + t), sweepAngle);
                    mPath.arcTo(ovalTop, 180 + (float) (180 + startAngle - t), -(180 + 2 * startAngle));
                    mPath.arcTo(ovalLeft, 180 + (float) (180 - startAngle - t), sweepAngle);
                } else {
                    mPath.addArc(ovalBottom, (float) (sweepAngle - startAngle + t), -(180 + 2 * (sweepAngle - startAngle)));
                    mPath.arcTo(ovalRight, (float) -(sweepAngle - startAngle - t), sweepAngle);
                    mPath.arcTo(ovalTop, (float) (180 + startAngle + t), -(180 + 2 * startAngle));
                    mPath.arcTo(ovalLeft, (float) (180 - startAngle + t), sweepAngle);
                }
                mPath.close();
            }

        }else{
            mPath.addArc(ovalBottom, 0, 360);
        }

        postInvalidate();
    }

//    private void  changeShape(){
//        mPath.reset();
//        //当外圆不能覆盖内圆时，开始显示内圆及切边，切边由左右圆计算
//        if (!ovalBottom.contains(ovalTop)){
//            isBreak();
//            if (isBreak){
//                isPull = false;
//                mPath.addArc(ovalBottom, 0, 360);
//            }else {
//                isPull = true;
//                //外圆圆心和内圆圆心的距离
//                mathE = Math.sqrt(Math.pow(bottomX - topX, 2) + Math.pow(bottomY - topY, 2));
//                //XY用于计算弧度开始角和弧度角
//                //当左圆心位于E上的垂点不在E上时，X为负
//                mathX = (2 * topRadius * leftRadius + Math.pow(topRadius, 2) + Math.pow(mathE, 2) - Math.pow(bottomRadius, 2) - 2 * bottomRadius * leftRadius) / (2 * mathE);
//                mathY = Math.sqrt(Math.pow(topRadius + leftRadius, 2) - Math.pow(mathX, 2));
//                //Log.e("math", "mathX-->"+mathX+"--mathY-->"+mathY);
//                //开始角跟随X的正负变化，不影响整体的计算方式和逻辑
//                startAngle = (float) (Math.atan(mathX / mathY) / Math.PI * 180);
//                sweepAngle = (float) (Math.atan((mathE - mathX) / mathY) / Math.PI * 180 + startAngle);
//
//                //Log.e("angle", "startAngle-->"+startAngle+"--sweep-->"+sweepAngle);
//                //t是外圆圆心到内圆圆心的夹角的余角
//                //t在一、三象限是负，二、四象限是正
//                double t = Math.atan((bottomX - topX) / (bottomY - topY)) / Math.PI * 180;
//                //startAngle + t 是左圆圆心到内圆圆心的夹角
//                //取负是计算的需要   例如：左圆圆心和外圆圆心都在第一象限，
//                //此时startAngle + t是正，得到的反余弦和反正弦都为正，但是计算坐标需要的正余弦应该是负
//                //完整理解需要画图，及在四个象限的各两种情况
//                double tan = -Math.toRadians(startAngle + t);
//                //当左圆圆心在内圆圆心下方时，左圆圆心到内圆圆心的夹角已大于180，而上面的计算结果是减去了180的
//                if (bottomY > topY)
//                    tan += Math.toRadians(180);
//                //通过0~360的夹角的正反余弦计算距离进而得到坐标
//                double n = (topRadius + leftRadius) * Math.cos(tan);
//                double m = (topRadius + leftRadius) * Math.sin(tan);
//
//                leftX = topX + n;
//                leftY = topY + m;
//                ovalLeft = new RectF((float) (leftX - leftRadius), (float) (leftY - leftRadius), (float) (leftX + leftRadius), (float) (leftY + leftRadius));
//
//                double tan1 = -Math.toRadians(180 - startAngle + t);
//                if (bottomY > topY)
//                    tan1 += Math.toRadians(180);
//                double n1 = (topRadius + leftRadius) * Math.cos(tan1);
//                double m1 = (topRadius + leftRadius) * Math.sin(tan1);
//                rightX = topX + n1;
//                rightY = topY + m1;
//                ovalRight = new RectF((float) (rightX - rightRadius), (float) (rightY - rightRadius), (float) (rightX + rightRadius), (float) (rightY + rightRadius));
//                //弧度开始角也是一样，
//                if (bottomY > topY) {
//                    mPath.addArc(ovalBottom, 180 + (float) (sweepAngle - startAngle - t), -(180 + 2 * (sweepAngle - startAngle)));
//                    mPath.arcTo(ovalRight, 180 + (float) -(sweepAngle - startAngle + t), sweepAngle);
//                    mPath.arcTo(ovalTop, 180 + (float) (180 + startAngle - t), -(180 + 2 * startAngle));
//                    mPath.arcTo(ovalLeft, 180 + (float) (180 - startAngle - t), sweepAngle);
//                } else {
//                    mPath.addArc(ovalBottom, (float) (sweepAngle - startAngle - t), -(180 + 2 * (sweepAngle - startAngle)));
//                    mPath.arcTo(ovalRight, (float) -(sweepAngle - startAngle + t), sweepAngle);
//                    mPath.arcTo(ovalTop, (float) (180 + startAngle - t), -(180 + 2 * startAngle));
//                    mPath.arcTo(ovalLeft, (float) (180 - startAngle - t), sweepAngle);
//                }
//                mPath.close();
//
//                //右圆圆心通过坐标计算得出，但计算结果是四个坐标，其中一个还是左圆圆心，还需要计算到外圆圆心距离来判断哪个是右圆圆心
//                //计算方式为一个点到另两个已知点的距离为固定值，a、b、c为计算过程中的中间值，木有意义
//                //                        double a = Math.pow(bottomRadius + leftRadius, 2) - Math.pow(topRadius + leftRadius, 2) - Math.pow(topX - bottomX, 2)
//                //                                - Math.pow(bottomY, 2) + Math.pow(topY, 2);
//                //                        double b = (topY * Math.pow(topX - bottomX, 2) + a * (topY - bottomY) / 2)
//                //                                / (Math.pow(topX - bottomX, 2) + Math.pow(topY - bottomY, 2));
//                //                        double c = (Math.pow(topRadius + leftRadius, 2) * Math.pow(topX - bottomX, 2) - Math.pow(topY, 2)
//                //                                * Math.pow(topX - bottomX, 2) - Math.pow(a, 2) / 4)
//                //                                / (Math.pow(topX - bottomX, 2) + Math.pow(topY - bottomY, 2));
//                //                        //当外圆圆心在内圆圆心左边，leftY = -ly+b, rightY = ly+b;在右边时则反过来
//                //                        double ly = Math.sqrt(c + Math.pow(b, 2));
//                //
//                //                        if (bottomX < topX){
//                //                            rightY = ly+b;
//                //
//                //                            double rx = Math.sqrt(Math.pow(topRadius + leftRadius, 2) - Math.pow(rightY - topY, 2));
//                //                            //jl为右圆圆心到外圆圆心距离，再用绝对差值得出正确的rightX
//                //                            double jl = Math.sqrt(Math.pow(rightY - bottomY, 2) + Math.pow(rx + topX - bottomX, 2));
//                //                            if (Math.abs(jl - bottomRadius - rightRadius) < 0.1)
//                //                                rightX = rx + topX;
//                //                            else
//                //                                rightX = -rx + topX;
//                //                        } else {
//                //                            rightY = -ly+b;
//                //
//                //                            double rx = Math.sqrt(Math.pow(topRadius + leftRadius, 2) - Math.pow(rightY - topY, 2));
//                //                            double jl = Math.sqrt(Math.pow(rightY - bottomY, 2) + Math.pow(rx + topX - bottomX, 2));
//                //                            if (Math.abs(jl - bottomRadius - rightRadius) < 0.1)
//                //                                rightX = rx + topX;
//                //                            else
//                //                                rightX = -rx + topX;
//                //                        }
//            }
//
//        }else{
//            mPath.addArc(ovalBottom, 0, 360);
//        }
//
//        postInvalidate();
//    }
}

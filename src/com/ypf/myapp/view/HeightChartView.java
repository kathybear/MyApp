package com.ypf.myapp.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.ypf.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ypf on 2016/2/28.
 */
public class HeightChartView extends View {
    private boolean first = false;
    private int baseHeight;
    private int mWidth1;
    private int mHeight;
    private Rect rect;
    private Rect rect1;
    private Rect mTextBound;

    private float moveY;
    private Bitmap mImage;
    //private Bitmap scale_bg;
    private Paint mPaint;
    private int textcolor;
    private int bgcolor;
    private int linecolor1;
    private int linecolor2;
    private int d1 = 50;
    private int d2 = 70;
    private int d3 = 90;
    private int d4 = 30;
    private int d5 = 200;
    private int size;
    //	private int scalelength = 34;
    private List<Scale> scales = new ArrayList<Scale>();
    private boolean ismove = false;
    private int chooseheight = 170;

    public HeightChartView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        inti();
    }

    public HeightChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        inti();
    }

    public HeightChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        inti();
    }

    private void inti()
    {
        mImage = BitmapFactory.decodeResource(getResources(), R.drawable.height_jiantou_03);
        //scale_bg = BitmapFactory.decodeResource(getResources(), R.drawable.height_chi_03_);
        mPaint = new Paint();
        rect = new Rect();
        rect1 = new Rect();
        textcolor = Color.parseColor("#1cbdbf");
        linecolor1 = Color.parseColor("#d2d2d2");
        linecolor2 = Color.parseColor("#5a5a5a");
        bgcolor = Color.parseColor("#ffffff");

        mTextBound = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        mWidth1 = specSize;

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        mHeight = specSize;

        if(!first)
        {
            rect.left = 0;
            rect.right = mImage.getWidth();
            rect.top = 15;
            rect.bottom = mImage.getHeight() + 15;
            baseHeight = mImage.getHeight() / 2 + 15;//35

            first = true;
            rect1.left = 0;
            rect1.right = mWidth1;
            rect1.top = 0;
            rect1.bottom = mHeight;

            size = mHeight / d4 + 3;
            Scale scale = null;
            scales.clear();
            for(int i = -1; i < size; i++)
            {
                if(i % 10 == 0)
                {
                    scale = new Scale();
                    scale.setScale(170 - i);
                    scale.setY(d4 * i);
                    scales.add(scale);
                }
                else if(i % 5 == 0)
                {
                    scale = new Scale();
                    scale.setScale(170 - i);
                    scale.setY(d4 * i);
                    scales.add(scale);
                }
                else
                {
                    scale = new Scale();
                    scale.setScale(170 - i);
                    scale.setY(d4 * i);
                    scales.add(scale);
                }
            }
        }

    }


    private boolean checkPoint(MotionEvent event)
    {
        if(event.getX() > mWidth1)
            return false;
        else
            return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                moveY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(!ismove && checkPoint(event))
                {
                    ismove = true;
                    float dis = event.getY() - moveY;
                    Scale scale = null;
                    for(int i = 0; i < size + 1; i++)
                    {
                        float change = scales.get(i).getY() + dis;
                        if(i == 0 && change < -baseHeight)
                        {
                            scales.remove(0);
                            scale = new Scale();
                            scale.setScale(scales.get(scales.size() - 1).getScale() - 1);
                            scale.setY(scales.get(scales.size() - 1).getY() + d4);
                            scales.add(scale);
                        }
                        else if(i == 0 && change > -baseHeight)
                        {
                            scales.remove(scales.size() - 1);
                            scale = new Scale();
                            scale.setScale(scales.get(0).getScale() + 1);
                            scale.setY(scales.get(0).getY() - d4);
                            scales.add(0, scale);
                        }
                        scale = scales.get(i);
                        scale.setY(scale.getY() + dis);
                    }
                    for(int i = 0; i < size + 1; i++)
                    {
                        if(scales.get(i).getY() >= 0)
                        {
                            chooseheight = scales.get(i).getScale();
                            if (null != listener)
                            listener.onChanged(scales.get(i).getScale());
                            break;
                        }
                    }
                    moveY = event.getY();
                    ismove = false;
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    private HeightOnChangedListener listener = null;

    public void setHeightOnChangedListener(HeightOnChangedListener listener){
        this.listener = listener;
    }

    public interface HeightOnChangedListener{
        public void onChanged(float change);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        mPaint.setColor(bgcolor);
        canvas.drawRect(5, 5, mWidth1-5, mHeight-5, mPaint);

        mPaint.setStrokeWidth(4);
        Scale scale = null;
        for(int i = 0; i < size + 1; i++)
        {
            scale = scales.get(i);
            String ss = "" + scale.getScale();
            char c = ss.charAt(ss.length() - 1);
            if(c == '0')
            {
                mPaint.setColor(textcolor);
                mPaint.setTextSize(50);
                mPaint.getTextBounds(ss, 0, ss.length(), mTextBound);
                canvas.drawText(ss, mWidth1 - d5, baseHeight + scale.getY() + mTextBound.height() / 2, mPaint);
                mPaint.setColor(linecolor2);
                canvas.drawLine(mWidth1 - d3, baseHeight + scale.getY(), mWidth1, baseHeight + scale.getY(), mPaint);
            }
            else if(c == '5')
            {
                mPaint.setColor(linecolor2);
                canvas.drawLine(mWidth1 - d2, baseHeight + scale.getY(), mWidth1, baseHeight + scale.getY(), mPaint);
            }
            else
            {
                mPaint.setColor(linecolor1);
                canvas.drawLine(mWidth1 - d1, baseHeight + scale.getY(), mWidth1, baseHeight + scale.getY(), mPaint);
            }
        }
        canvas.drawBitmap(mImage, null, rect, mPaint);
        //canvas.drawBitmap(scale_bg, null, rect1, mPaint);
    }

    public int getChooseheight() {
        return chooseheight;
    }

    public void setChooseheight(int chooseheight) {
        this.chooseheight = chooseheight;
    }

    private class Scale
    {
        private int scale;
        //		private int length;
        private float y;
        //		private int x;

        //		public int getX() {
        //			return x;
        //		}
        //		public void setX(int x) {
        //			this.x = x;
        //		}
        public int getScale() {
            return scale;
        }
        public void setScale(int scale) {
            this.scale = scale;
        }
        //		public int getLength() {
        //			return length;
        //		}
        //		public void setLength(int length) {
        //			this.length = length;
        //		}
        public float getY() {
            return y;
        }
        public void setY(float y) {
            this.y = y;
        }

    }
}

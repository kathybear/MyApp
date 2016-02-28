package com.ypf.myapp.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.ypf.myapp.R;
import com.ypf.myapp.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ypf on 2016/2/28.
 */
public class WeightChartView extends View {
    private boolean first = false;
    private Context context;
    private int baseHeight;

    private int mWidth;
    private int mHeight;
    private Rect rect;
    private Rect rect1;
    private Rect mTextBound;

    private float moveX;
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
    private int d5 = 150;
    private int size;
    //	private int scalelength = 34;
    private List<Scale> scales = new ArrayList<Scale>();
    private boolean ismove = false;
    private int chooseheight = 60;

    public WeightChartView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        inti();
    }

    public WeightChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        this.context = context;
        inti();
    }

    public WeightChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
        inti();
    }

    private void inti()
    {
        mImage = BitmapFactory.decodeResource(getResources(), R.drawable.jiantou_03);
        //scale_bg = BitmapFactory.decodeResource(getResources(), R.drawable.chidu_07_);
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
        mWidth = specSize;


        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        mHeight = specSize;

        if(!first)
        {
            first = true;
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = 0;
            rect.bottom = mImage.getHeight();
            baseHeight = mWidth / 2;

            rect1.left = 0;
            rect1.right = mWidth;
            rect1.top = 0;
            rect1.bottom = DensityUtil.dp2px(context, 100);

            size = mWidth / d4 + 3;
            Scale scale = null;
            scales.clear();
            for(int i = - size / 2; i < size / 2; i++)
            {
                if(i % 10 == 0)
                {
                    scale = new Scale();
                    scale.setScale(60 - i);
                    scale.setX(d4 * i);
                    scales.add(scale);
                }
                else if(i % 5 == 0)
                {
                    scale = new Scale();
                    scale.setScale(60 - i);
                    scale.setX(d4 * i);
                    scales.add(scale);
                }
                else
                {
                    scale = new Scale();
                    scale.setScale(60 - i);
                    scale.setX(d4 * i);
                    scales.add(scale);
                }
            }
        }

    }

    private boolean checkPoint(MotionEvent event)
    {
        if(event.getY() < rect1.top)
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
                moveX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if(!ismove && checkPoint(event))
                {
                    ismove = true;
                    float dis = event.getX() - moveX;
                    Scale scale = null;
                    for(int i = 0; i < scales.size(); i++)
                    {
                        float change = scales.get(i).getX() + dis;
                        //				System.out.println("" + scale.getScale()+ "-->" + change);
                        if(i == 0 && change < - size / 2 * d4)
                        {
                            scales.remove(0);
                            scale = new Scale();
                            scale.setScale(scales.get(scales.size() - 1).getScale() - 1);
                            scale.setX(scales.get(scales.size() - 1).getX() + d4);
                            scales.add(scale);
                        }
                        else if(i == 0 && change > - size / 2 * d4)
                        {
                            scales.remove(scales.size() - 1);
                            scale = new Scale();
                            scale.setScale(scales.get(0).getScale() + 1);
                            scale.setX(scales.get(0).getX() - d4);
                            scales.add(0, scale);
                        }
                        scale = scales.get(i);
                        scale.setX(scale.getX() + dis);
                        //					System.out.println(scales.get(i).getX());
                    }
                    for(int i = 0; i < scales.size(); i++)
                    {
                        scale = scales.get(i);
                        if(scale.getX() >= 0)
                        {
                            setChooseheight(scale.getScale());
                            if (null != listener)
                                listener.onChanged(scale.getScale());
                            break;
                        }
                    }
                    moveX = event.getX();
                    ismove = false;
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    private WeightOnChangedListener listener = null;

    public void setWeightOnChangedListener(WeightOnChangedListener listener){
        this.listener = listener;
    }

    public interface WeightOnChangedListener{
        public void onChanged(float change);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        mPaint.setColor(bgcolor);
        canvas.drawRect(5, 5, mWidth-5, mHeight-5, mPaint);
        canvas.drawBitmap(mImage, null, rect, mPaint);

        mPaint.setStrokeWidth(4);
        Scale scale = null;
        for(int i = 0; i < scales.size(); i++)
        {
            scale = scales.get(i);
            String ss = "" + scale.getScale();
            char c = ss.charAt(ss.length() - 1);
            if(c == '0')
            {
                mPaint.setColor(textcolor);
                mPaint.setTextSize(50);
                mPaint.getTextBounds(ss, 0, ss.length(), mTextBound);
                canvas.drawText(ss, baseHeight + scale.getX() - mTextBound.width() / 2, mHeight - d5, mPaint);
                mPaint.setColor(linecolor2);
                canvas.drawLine(baseHeight + scale.getX(), mHeight - d3, baseHeight + scale.getX(), mHeight, mPaint);
            }
            else if(c == '5')
            {
                mPaint.setColor(linecolor2);
                canvas.drawLine(baseHeight + scale.getX(), mHeight - d2, baseHeight + scale.getX(), mHeight, mPaint);
            }
            else
            {
                mPaint.setColor(linecolor1);
                canvas.drawLine(baseHeight + scale.getX(), mHeight - d1, baseHeight + scale.getX(), mHeight, mPaint);
            }
        }
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
        //		private float y;
        private float x;

        public float getX() {
            return x;
        }
        public void setX(float x) {
            this.x = x;
        }
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
        //		public float getY() {
        //			return y;
        //		}
        //		public void setY(float y) {
        //			this.y = y;
        //		}

    }
}

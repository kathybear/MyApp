package com.ypf.myapp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import com.ypf.myapp.R;
import com.ypf.myapp.adapter.AgeAdapter;
import com.ypf.myapp.utils.DensityUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ypf on 2016/2/28.
 */
public class DateShowActivity extends Activity {
    private Context context;
    private ListView listview_year;
    private ListView listview_month;
    private ListView listview_day;
    private AgeAdapter yearAdapter;
    private AgeAdapter monthAdapter;
    private AgeAdapter dayAdapter;
    private List<Integer> year = new ArrayList<Integer>();
    private List<Integer> month = new ArrayList<Integer>();
    private List<Integer> day = new ArrayList<Integer>();
    private int size;
    private int size1;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateshow);
        context = this;

        size = DensityUtil.dp2px(context, 30);
        size1 = DensityUtil.dp2px(context, 90);

        listview_year = (ListView) findViewById(R.id.listview_year);
        listview_month = (ListView) findViewById(R.id.listview_month);
        listview_day = (ListView) findViewById(R.id.listview_day);
        yearAdapter = new AgeAdapter(context, R.layout.fragment_age_listview_item, year);
        monthAdapter = new AgeAdapter(context, R.layout.fragment_age_listview_item, month);
        dayAdapter = new AgeAdapter(context, R.layout.fragment_age_listview_item, day);
        listview_year.setAdapter(yearAdapter);
        listview_month.setAdapter(monthAdapter);
        listview_day.setAdapter(dayAdapter);
        listview_year.setOnScrollListener(yearOnScrollListener);
        listview_month.setOnScrollListener(monthOnScrollListener);
        listview_day.setOnScrollListener(dayOnScrollListener);

        initdata();
    }

    public void initdata(){
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        List<Integer> list = new ArrayList<Integer>();
        for(int i = mYear; i >= 1900; i--)
            list.add(i);
        year.clear();
        year.addAll(list);
        yearAdapter.notifyDataSetChanged();
        list.clear();
        for(int i = 1; i <= 12; i++)
            list.add(i);
        month.clear();
        month.addAll(list);
        monthAdapter.notifyDataSetChanged();
        list.clear();
        for(int i = 1; i <= getCurrentMonthDay(mYear, mMonth - 1); i++)
            list.add(i);
        day.clear();
        day.addAll(list);
        dayAdapter.notifyDataSetChanged();
    }

    /**
     * 获取当月的 天数
     * */
    public int getCurrentMonthDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    @Override
    protected void onStart() {
        super.onStart();
        listview_year.setSelectionFromTop(1, size);
        listview_month.setSelectionFromTop(mMonth, size);
        listview_day.setSelectionFromTop(mDay, size);
    }

    private OnScrollListener yearOnScrollListener = new OnScrollListener() {
        private int start_index, end_index;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // TODO Auto-generated method stub
            switch (scrollState) {
                case OnScrollListener.SCROLL_STATE_IDLE:// 滑动停止
                    System.out.println("" + start_index + "-->" + end_index);
                    if(start_index == 0)
                    {
                        listview_year.setSelectionFromTop(1, size);
                        mYear = year.get(0);
                    }
                    else if(end_index == mYear - 1900 + 2)
                    {
                        listview_year.setSelectionFromTop(end_index - 1, size);
                        mYear = 1900;
                    }
                    else
                    {
                        View cur = listview_year.getChildAt(0);
                        int position;
                        if(cur.getTop() < (-size1 / 2)){
                            position = listview_year.getPositionForView(listview_year.getChildAt(1));
                            mYear = year.get(position - 1);
                        }else{
                            position = listview_year.getPositionForView(cur);
                            mYear = year.get(position - 1);
                        }
                        listview_year.setSelectionFromTop(position, size);
                    }
                    List<Integer> list = new ArrayList<Integer>();
                    int count = getCurrentMonthDay(mYear, mMonth - 1);
                    for(int i = 1; i <= count; i++)
                        list.add(i);
                    day.clear();
                    day.addAll(list);
                    dayAdapter.notifyDataSetChanged();
                    if(mDay - count > 0)
                    {
                        mDay = count;
                        listview_day.setSelectionFromTop(count, size);
                    }
                    else
                    {
                        listview_day.setSelectionFromTop(mDay, size);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            // TODO Auto-generated method stub
            start_index = firstVisibleItem;
            end_index = firstVisibleItem + visibleItemCount - 1;
        }

    };

    private OnScrollListener monthOnScrollListener = new OnScrollListener() {
        private int start_index, end_index;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // TODO Auto-generated method stub
            switch (scrollState) {
                case OnScrollListener.SCROLL_STATE_IDLE:// 滑动停止
                    System.out.println("" + start_index + "-->" + 0);
                    if(start_index == 0)
                    {
                        listview_month.setSelectionFromTop(1, size);
                        mMonth = 1;
                    }
                    else if(end_index == 13)
                    {
                        listview_month.setSelectionFromTop(12, size);
                        mMonth = 12;
                    }
                    else
                    {
                        View cur = listview_month.getChildAt(0);
                        int position;
                        if(cur.getTop() < (-size1 / 2)){
                            position = listview_month.getPositionForView(listview_month.getChildAt(1));
                            mMonth = month.get(position - 1);
                        }else{
                            position = listview_month.getPositionForView(cur);
                            mMonth = month.get(position - 1);
                        }
                        listview_month.setSelectionFromTop(position, size);
                    }
                    List<Integer> list = new ArrayList<Integer>();
                    int count = getCurrentMonthDay(mYear, mMonth - 1);
                    for(int i = 1; i <= count; i++)
                        list.add(i);
                    day.clear();
                    day.addAll(list);
                    dayAdapter.notifyDataSetChanged();
                    if(mDay - count > 0)
                    {
                        mDay = count;
                        listview_day.setSelectionFromTop(count, size);
                    }
                    else
                    {
                        listview_day.setSelectionFromTop(mDay, size);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            // TODO Auto-generated method stub
            start_index = firstVisibleItem;
            end_index = firstVisibleItem + visibleItemCount - 1;
        }

    };

    private OnScrollListener dayOnScrollListener = new OnScrollListener() {
        private int start_index, end_index;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // TODO Auto-generated method stub
            switch (scrollState) {
                case OnScrollListener.SCROLL_STATE_IDLE:// 滑动停止
                    System.out.println("" + start_index + "-->" + end_index);
                    if(start_index == 0)
                    {
                        listview_day.setSelectionFromTop(1, size);
                        mDay = 1;
                    }
                    else if(end_index == day.size() + 1)
                    {
                        listview_day.setSelectionFromTop(day.size(), size);
                        mDay = day.size();
                    }
                    else
                    {
                        View cur = listview_day.getChildAt(0);
                        int position;
                        if(cur.getTop() < (-size1 / 2)){
                            position = listview_day.getPositionForView(listview_day.getChildAt(1));
                            mDay = day.get(position - 1);
                        }else{
                            position = listview_day.getPositionForView(cur);
                            mDay = day.get(position - 1);
                        }
                        listview_day.setSelectionFromTop(position, size);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            // TODO Auto-generated method stub
            start_index = firstVisibleItem;
            end_index = firstVisibleItem + visibleItemCount - 1;
        }

    };
}
package com.ypf.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ypf.myapp.R;

import java.util.List;

/**
 * Created by ypf on 2015/12/18.
 */
public class MyAdapter extends BaseAdapter {
    private List<String> items;
    private Context context;
    private int layoutID = -1;

    public MyAdapter(List<String> items, Context context, int layoutID) {
        this(context, items);
        this.layoutID = layoutID;
    }

    public MyAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView tv;
        if (layoutID == -1) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
            tv = (TextView) view.findViewById(R.id.name_tv);
        } else {
            view = LayoutInflater.from(context).inflate(layoutID, parent, false);
        }
        tv.setText(items.get(position));
        return view;
    }
}

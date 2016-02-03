package com.ypf.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ypf.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ypf on 2016/1/21.
 */
public class ChooseHistoryCityAdapter extends BaseAdapter {
    private Context context;
    private List<String> items = new ArrayList<String>();
    private int layoutID;

    public ChooseHistoryCityAdapter(Context context, List<String> items, int layoutID) {
        this.context = context;
        this.items = items;
        this.layoutID = layoutID;
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
        ViewHolder holder;
        if (null == convertView){
            convertView = LayoutInflater.from(context).inflate(layoutID, parent, false);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder{
        public TextView text;
    }
}

package com.ypf.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ypf.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/28.
 */
public class AgeAdapter extends ArrayAdapter<Integer> {
    private int textViewResourceId;
    private List<Integer> objects;
    private Context context;

    public AgeAdapter(Context context, int textViewResourceId,List<Integer> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.textViewResourceId = textViewResourceId;
        if(objects == null) objects = new ArrayList<Integer>();
        this.objects = objects;
    }

    @Override
    public void add(Integer object) {
        objects.add(object);
    }
    @Override
    public void clear() {
        objects.clear();
    }
    @Override
    public int getCount() {
        return objects.size() + 2;
    }
    @Override
    public Integer getItem(int position) {
        return objects.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        if(position == 0 || position == objects.size() + 1)
            return 0;
        else
            return 1;
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ViewHolder1 holder1 = null;
        int type = getItemViewType(position);
        if(convertView == null){
            if(type == 0){
                convertView = LayoutInflater.from(context).inflate(R.layout.fragment_age_listview_item1, parent, false);
                holder = new ViewHolder();
                holder.content = (TextView) convertView.findViewById(R.id.content);

                convertView.setTag(R.id.tag_0, holder1);
            }else{
                convertView = LayoutInflater.from(context).inflate(textViewResourceId, parent, false);
                holder1 = new ViewHolder1();
                holder1.content = (TextView) convertView.findViewById(R.id.content);

                convertView.setTag(R.id.tag_1, holder1);
            }

        }else{
            if(type == 0){
                holder = (ViewHolder) convertView.getTag(R.id.tag_0);
            }else{
                holder1 = (ViewHolder1) convertView.getTag(R.id.tag_1);
            }

        }
        if(type == 0){

        }else{
            holder1.content.setText("" + objects.get(position - 1));
        }
        return convertView;
    }

    public class ViewHolder{
        public TextView content;
    }

    public class ViewHolder1{
        public TextView content;
    }
}

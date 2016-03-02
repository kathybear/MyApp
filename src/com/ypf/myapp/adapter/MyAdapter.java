package com.ypf.myapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ypf.myapp.R;
import com.ypf.myapp.common.MyConstants;

import java.util.List;

/**
 * Created by ypf on 2015/12/18.
 */
public class MyAdapter extends BaseAdapter {
    private List<String> items;
    private Context context;
    private int layoutID = -1;
    private Handler handler;

    public MyAdapter(List<String> items, Context context, int layoutID) {
        this(context, items);
        this.layoutID = layoutID;
    }

    public MyAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    public MyAdapter(Context context, List<String> items, Handler handler) {
        this(context, items);
        this.handler = handler;
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
        TextView textView_delete = null;
        //item layout中需加入id=common_text的textView
        if (layoutID == -1) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
            tv = (TextView) view.findViewById(R.id.name_tv);
            textView_delete = (TextView) view.findViewById(R.id.textView_delete);
        } else {
            view = LayoutInflater.from(context).inflate(layoutID, parent, false);
            tv = (TextView) view.findViewById(R.id.common_text);
        }
        tv.setText(items.get(position));
        if (null != textView_delete)
            textView_delete.setOnClickListener(new MyOnClickListener(position));
        return view;
    }

    class MyOnClickListener implements View.OnClickListener {
        private int position;

        public MyOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.textView_delete:
                    Message msg = handler.obtainMessage();
                    msg.what = MyConstants.SLIDEBACK;
                    msg.arg1 = position;
                    handler.sendMessage(msg);
                    break;
            }
        }
    }
}

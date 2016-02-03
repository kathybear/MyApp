package com.ypf.myapp.tools;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.PopupWindow;
import com.ypf.myapp.R;
import com.ypf.myapp.adapter.ChooseHistoryCityAdapter;
import com.ypf.myapp.utils.CommonUtil;
import com.ypf.myapp.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ypf on 2016/1/21.
 */
public class ChooseCity {
    private Context context;
    private PopupWindow pw;
    private int viewBottom;

    public ChooseCity(Context context, int viewBottom) {
        this.context = context;
        this.viewBottom = viewBottom;
    }

    public void init(){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_choosecity, null);
        GridView gridView = (GridView) view.findViewById(R.id.grid_history);
        List<String> items = new ArrayList<String>();
        for (int i = 0; i < 5; i++)
            items.add("++");
        gridView.setAdapter(new ChooseHistoryCityAdapter(context, items, R.layout.layout_choosecity_item));
        GridView gridView1 = (GridView) view.findViewById(R.id.grid_choose);
        List<String> items1 = new ArrayList<String>();
        for (int i = 0; i < 50; i++)
            items1.add("++");
        gridView1.setAdapter(new ChooseHistoryCityAdapter(context, items1, R.layout.layout_choosecity_item1));
        pw = new PopupWindow(view, ScreenUtil.getScreenWidth(context), ScreenUtil.getScreenHeight(context) - viewBottom, true);
        pw.setBackgroundDrawable(new ColorDrawable(0x00000000));
        pw.setOutsideTouchable(true);
    }

    public void show(View view){
        if (null != pw){
            pw.showAsDropDown(view);
        }
    }

    public void dismiss(){
        if (null != pw)
            pw.dismiss();
    }
}

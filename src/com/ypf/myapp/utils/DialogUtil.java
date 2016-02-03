package com.ypf.myapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import com.ypf.myapp.R;

/**
 * Created by ypf on 2016/1/11.
 */
public class DialogUtil {
    private Context context;
    private Dialog dialog;
    private Window window;

    public DialogUtil(Context context, int layout) {
        this(context);
        dialog.setContentView(layout);
        window = dialog.getWindow();
    }

    public DialogUtil(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.dialog);
    }

    public DialogUtil() {
    }

    public void show() {
        if (null != dialog)
            dialog.show();
    }

    public void dismiss() {
        if (null != dialog)
            dialog.dismiss();
    }

    public void hide() {
        if (null != dialog)
            dialog.hide();
    }
}

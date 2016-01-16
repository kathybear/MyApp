package com.ypf.myapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.ypf.myapp.R;
import com.ypf.myapp.common.MyConstants;
import com.ypf.myapp.utils.CommonUtil;
import com.ypf.myapp.utils.IntentsUtil;

/**
 * Created by ypf on 2016/1/15.
 */
public class UncaughtExceptionActivity extends Activity implements View.OnClickListener {
    private String s;
    private Context context;
    private EditText editText;
    private Button button_getPicture;
    private ImageView image_picture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncaughtexception);
//        System.out.println(s.equals("any string"));
        context = this;

        editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")){
                    if (s.toString().indexOf(".") == 0){
                        editText.setText("0.");
                        editText.setSelection(2);
                    } else {
                        if (s.toString().indexOf(".") != s.length() - 1){
                            String[] strs = s.toString().split("\\.");
                            if (strs[0].length() > 4){
                                editText.setText(strs[0].subSequence(0, 4)+"."+strs[1]);
                                editText.setSelection(editText.getText().length());
                            }
                            if (strs[1].length() > 1){
                                editText.setText(strs[0]+"."+strs[1].subSequence(0, 1));
                                editText.setSelection(editText.getText().length());
                            }
                        }

                    }
                } else {
                    if (s.toString().length() > 4){
                        editText.setText(s.subSequence(0, 4));
                        editText.setSelection(4);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        button_getPicture = (Button) findViewById(R.id.button_getPicture);
        button_getPicture.setOnClickListener(this);
        image_picture = (ImageView) findViewById(R.id.image_picture);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_getPicture:
                IntentsUtil.skipPhonePicForResult(context, MyConstants.GET_PHONE_PICTURE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case MyConstants.GET_PHONE_PICTURE:
                String imageUrl = CommonUtil.getUrlFromPhonePicture(context, resultCode, data);
                image_picture.setImageBitmap(BitmapFactory.decodeFile(imageUrl));
                break;
        }
    }
}
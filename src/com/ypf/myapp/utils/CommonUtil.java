package com.ypf.myapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ypf on 2016/1/11.
 */
public class CommonUtil {
    //获取本地路径中所有图片路径
    public static List<String> getImagePaths(String allImgPath)
    {
        // TODO Auto-generated method stub
        String dbDir = SDCardUtil.getSDCardPath();

        List<String> allImgFilePath =new ArrayList<String>();
        File file = new File(allImgPath);
        File[] files = file.listFiles();
        if(null==files)
        {
            return allImgFilePath;
        }
        for (int i = 0; i < files.length; i++)
        {
            String name = files[i].getName();
            if ( files[i].isFile() & name.endsWith(".jpg") || name.endsWith(".png")
                    || name.endsWith(".bmp") || name.endsWith(".gif") || name.endsWith(".jpeg"))
            {
                allImgFilePath.add(allImgPath.replace(dbDir,"")+files[i].getName());
            }
        }
        return allImgFilePath;
    }

    public static String getUrlFromPhonePicture(Context context, int resultCode, Intent data){
        if (resultCode == ((Activity)context).RESULT_OK) {
            if (data != null && data.getData() != null) {
                String imgFile = getPath(context, data.getData());
                if (!TextUtils.isEmpty(imgFile)) {
                    return imgFile;
                }
            }
        }
        return "";
    }

    public static String getPath(Context context, Uri uri) {
        if (uri.toString().contains("content:")){
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = context.getContentResolver().query(uri, filePathColumn,
                    null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            return picturePath;
        } else if (uri.toString().contains("file:")){//部分小米手机直接返回图片url，直接取
            return uri.toString().replace("file://", "");
        }
        return "";
    }
}

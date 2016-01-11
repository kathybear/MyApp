package com.ypf.myapp.utils;

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
}

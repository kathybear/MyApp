package com.ypf.myapp.utils;

import java.io.*;

/**
 * Created by ypf on 2016/1/11.
 */
public class FileUtil {
    public static boolean copyFile(InputStream fosfrom, OutputStream fosto)
    {
        try
        {
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0)
            {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    public static boolean copyFile(String fromFilePath, String toFilePath)
    {
        return copyFile(new File(fromFilePath), new File(toFilePath));
    }

    public static boolean copyFile(File fromFile, File toFile)
    {
        try
        {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            return copyFile(fosfrom, fosto);
        } catch (Exception ex){
            return false;
        }
    }

    public static void saveFile(byte[] data, String toFilePath) throws IOException
    {
        saveFile(data, new File(toFilePath));
    }

    public static void saveFile(byte[] data, File toFile) throws IOException
    {
        OutputStream fosto = new FileOutputStream(toFile);
        fosto.write(data);
        fosto.close();
    }

    public static void saveFile(InputStream is, String toFilePath) throws IOException
    {
        saveFile(is, new File(toFilePath));
    }

    public static void saveFile(InputStream is, File toFile) throws IOException
    {
        OutputStream fosto = new FileOutputStream(toFile);
        byte buffer[] = new byte[1024];
        int len = 0;
        while((len = is.read(buffer)) != -1)
        {
            fosto.write(buffer,0,len);
        }
        fosto.close();
        is.close();
    }

    //递归删除目录中所有文件及文件夹
    public static boolean deleteDir(File dir)
    {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++)
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success)
                {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}

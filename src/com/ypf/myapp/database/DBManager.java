package com.ypf.myapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager 
{
	private DatabaseHelper helper;
    private SQLiteDatabase db;
    private String sql="";
    
    public DBManager(Context context)
    {
    	helper = new DatabaseHelper(context);
    	db = helper.getWritableDatabase();
    }

}

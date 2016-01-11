package com.ypf.myapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper 
{
	private static final String DATABASE_NAME = "App.db";
    private static final int DATABASE_VERSION = 1;
    
	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);  
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*db.execSQL("CREATE TABLE IF NOT EXISTS edit" +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "uid VARCHAR, "
				+ "type INTEGER, "
				+ "doc_id INTEGER, "
				+ "content VARCHAR, "
				+ "local_id INTEGER"
				+ ")");
		*/
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

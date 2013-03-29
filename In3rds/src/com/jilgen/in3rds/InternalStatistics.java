package com.jilgen.in3rds;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class InternalStatistics extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "InternalStatistics";
    private static final String STATS_TABLE_NAME = "stats"; 
    private static final String STATS_TABLE_CREATE =
                "CREATE TABLE " + STATS_TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Time INTEGER, " +
                "Name TEXT, " +
                "Value TEXT " +
                ");";

	public InternalStatistics(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	};
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(STATS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}

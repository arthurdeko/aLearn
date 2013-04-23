package com.jilgen.in3rds;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.text.format.Time;

public class StatsDatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "statsManager";
	private static final String TABLE_STATS = "stats";
	private static final String KEY_ID = "id";
	private static final String KEY_DATETIME = "datetime";
	private static final String KEY_BATTERY_STRENGTH = "batteryStrength";
	
	
	public StatsDatabaseHandler(Context context) {
		super(context,DATABASE_NAME , null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_STATS_TABLE="CREATE TABLE " + TABLE_STATS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_BATTERY_STRENGTH + " INTEGER,"
				+ KEY_DATETIME + " TEXT)";
		db.execSQL(CREATE_STATS_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_STATS);
		onCreate(db);
	}	
	
	public void addStat(InternalStats stats) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		Time datetime = new Time();
		datetime.setToNow();
		
		ContentValues values = new ContentValues();
		values.put(KEY_BATTERY_STRENGTH, stats.getBatteryStrength());
		values.put(KEY_DATETIME, datetime.format2445());
		
		db.insert(TABLE_STATS, null, values);
		db.close();
	}
	
	public void initialize() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STATS, null, null);
		db.close();
	}
	
	public List<InternalStats> getAllBatteryStrengths() {
		List<InternalStats> batteryStrengthList = new ArrayList<InternalStats>();
		
		String selectQuery = "SELECT " + KEY_DATETIME +"," + KEY_BATTERY_STRENGTH + " FROM " + TABLE_STATS;
		//String selectQuery = "SELECT " + KEY_BATTERY_STRENGTH + " FROM " + TABLE_STATS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()) {
			do {
				InternalStats internalStats = new InternalStats();
				internalStats.setDateTime(cursor.getString(0));
				internalStats.setBatteryStrength(Integer.parseInt(cursor.getString(1)));
				batteryStrengthList.add(internalStats);
			} while (cursor.moveToNext());
		}
		
		return batteryStrengthList;
	}

}

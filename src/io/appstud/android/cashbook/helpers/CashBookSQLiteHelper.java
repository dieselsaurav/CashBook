package io.appstud.android.cashbook.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CashBookSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_ENTRIES = "entries";
	public static final String COL_ID = "_id";
	public static final String COL_DESC = "description";
	public static final String COL_AMT = "amount";
	public static final String COL_FLAG = "flag";

	private static final String DB_NAME = "entries.db";
	private static final int DB_VER = 1;

	// database create sql statement
	private static final String DB_CREATE = "create table " + TABLE_ENTRIES
			+ "( " + COL_ID + " integer primary key autoincrement, " + COL_DESC
			+ " text not null, " + COL_AMT + " text not null, " + COL_FLAG
			+ " text not null);";

	public CashBookSQLiteHelper(Context context) {
		super(context, DB_NAME, null, DB_VER);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(CashBookSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_ENTRIES);
		onCreate(db);
	}

}

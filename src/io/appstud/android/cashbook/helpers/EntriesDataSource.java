package io.appstud.android.cashbook.helpers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EntriesDataSource {

	private SQLiteDatabase db;
	private CashBookSQLiteHelper dbHelper;
	private String[] allCols = { CashBookSQLiteHelper.COL_ID,
			CashBookSQLiteHelper.COL_DESC, CashBookSQLiteHelper.COL_AMT,
			CashBookSQLiteHelper.COL_FLAG };

	public EntriesDataSource(Context context) {
		dbHelper = new CashBookSQLiteHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Entry createEntry(String description, String amount, String flag) {
		ContentValues values = new ContentValues();
		values.put(CashBookSQLiteHelper.COL_DESC, description);
		values.put(CashBookSQLiteHelper.COL_AMT, amount);
		values.put(CashBookSQLiteHelper.COL_FLAG, flag);
		long insertId = db.insert(CashBookSQLiteHelper.TABLE_ENTRIES, null,
				values);
		Cursor cursor = db.query(CashBookSQLiteHelper.TABLE_ENTRIES, allCols,
				CashBookSQLiteHelper.COL_ID + " = " + insertId, null, null,
				null, null);
		cursor.moveToFirst();
		return cursorToEntry(cursor);
	}

	public void deleteEntry(Entry entry) {
		long id = entry.getId();
		Log.i("DATABASE CRUD", "Entry deleted with id: " + id);
		db.delete(CashBookSQLiteHelper.TABLE_ENTRIES,
				CashBookSQLiteHelper.COL_ID + " = " + id, null);
	}

	public List<Entry> getAllEntries() {
		List<Entry> entries = new ArrayList<Entry>();
		Cursor cursor = db.query(CashBookSQLiteHelper.TABLE_ENTRIES, allCols,
				null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Entry entry = cursorToEntry(cursor);
			entries.add(entry);
			cursor.moveToNext();
		}
		cursor.close();
		return entries;
	}

	private Entry cursorToEntry(Cursor cursor) {
		Entry entry = new Entry();
		entry.setId(cursor.getLong(0));
		entry.setDescription(cursor.getString(1));
		entry.setAmount(cursor.getString(2));
		entry.setFlag(cursor.getString(3));
		return entry;
	}
}

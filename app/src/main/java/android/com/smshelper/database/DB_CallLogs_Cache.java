package android.com.smshelper.database;

import android.com.smshelper.AppConstant;
import android.com.smshelper.entity.CallLogs;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15-1-21.
 */
public class DB_CallLogs_Cache extends SQLiteOpenHelper {
	private static DB_CallLogs_Cache instance;
	private final static int DB_VERSION = 1;

	private final String TABLE_CACHE = "A";
	private final String KEY_CACHENAME = "a";
	private final String KEY_NUMBER = "b";
	private final String KEY_TYPE = "c";
	private final String KEY_DATE = "d";
	private final String KEY_NUMBERAREA = "e";
	private final String KEY_NUMBERTYPE = "f";

	public static synchronized DB_CallLogs_Cache getInstance(Context context) {
		if (instance == null) {
			instance = new DB_CallLogs_Cache(context);
		}
		return instance;
	}

	public DB_CallLogs_Cache(Context context) {
		super(context, AppConstant.DB_CALLLOGS_CACHE, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(getCreateSQL());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	private String getCreateSQL() {
		return "CREATE TABLE IF NOT EXISTS " + TABLE_CACHE + "( "
				+ KEY_NUMBER + " VARCHAR(20) NOT NULL, "
				+ KEY_CACHENAME + " VARCHAR(40), "
				+ KEY_DATE + " LONG, "
				+ KEY_TYPE + " INT, "
				+ KEY_NUMBERAREA + " VARCHAR(30), "
				+ KEY_NUMBERTYPE + " VARCHAR(30) "
				+ ");";
	}

	public long getLastUpdateTime() {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT MAX(" + KEY_DATE + ") FROM " + TABLE_CACHE, null);
		long lastUpdateTime = 0L;
		try {
			if (cursor.moveToNext()) {
				lastUpdateTime = cursor.getLong(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return lastUpdateTime;
	}

	public void insert(CallLogs log) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			ContentValues cv = new ContentValues();
			cv.put(KEY_CACHENAME, log.getName());
			cv.put(KEY_DATE, log.getDate());
			cv.put(KEY_NUMBER, log.getPhone());
			cv.put(KEY_TYPE, log.getType());
			cv.put(KEY_NUMBERAREA, log.getNumArea());
			cv.put(KEY_NUMBERTYPE, log.getNumType());
			final long effect = db.insert(TABLE_CACHE, null, cv);
			Log.d("DB_CallLogs_Cache insert effect = ", String.valueOf(effect));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	public void insertAll(List<CallLogs> list) {
		for (CallLogs log : list) {
			final String phone = log.getPhone();
			if (contains(phone)) {
				delete(phone);
			}
			insert(log);
		}
		list.clear();
	}

	public List<CallLogs> getList() {
		List<CallLogs> result = new ArrayList<>();
		SQLiteDatabase db = getReadableDatabase();
		try {
			Cursor cursor = db.query(
					TABLE_CACHE,
					new String[]{
							KEY_CACHENAME,
							KEY_DATE,
							KEY_NUMBER,
							KEY_TYPE,
							KEY_NUMBERAREA,
							KEY_NUMBERTYPE,
					}
					,
					null,
					null,
					null,
					null,
					KEY_DATE + " DESC");
			while (cursor.moveToNext()) {
				String cacheName = cursor.getString(cursor.getColumnIndex(KEY_CACHENAME));
				long date = cursor.getLong(cursor.getColumnIndex(KEY_DATE));
				String number = cursor.getString(cursor.getColumnIndex(KEY_NUMBER));
				int type = cursor.getInt(cursor.getColumnIndex(KEY_TYPE));
				String mobileArea = cursor.getString(cursor.getColumnIndex(KEY_NUMBERAREA));
				String mobileType = cursor.getString(cursor.getColumnIndex(KEY_NUMBERTYPE));
				CallLogs log = new CallLogs(number, cacheName, date, type, mobileArea, mobileType);
				result.add(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return result;
	}

	public boolean contains(String phone) {
		boolean result = false;
		SQLiteDatabase db = getReadableDatabase();
		try {
			Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CACHE + "WHERE " + KEY_NUMBER + "=?",
					new String[]{phone});
			if (cursor.getCount() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				return false;
			}
		}
		return false;
	}

	private boolean delete(String phone) {
		boolean result = false;
		SQLiteDatabase db = getWritableDatabase();
		try {
			result = db.delete(TABLE_CACHE, KEY_NUMBER + "=?", new String[]{phone}) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return result;
	}
}

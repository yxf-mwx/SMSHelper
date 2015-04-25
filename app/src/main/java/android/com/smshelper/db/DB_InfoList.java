package android.com.smshelper.db;

import android.com.smshelper.entity.PeopleInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15-1-9.
 */
public class DB_InfoList extends SQLiteOpenHelper {
	public static DB_InfoList instance = null;
	public static Context mContext;
	private final static int VERSION_NAME = 1;
	private final static String DB_NAME = "INFO";
	private final static String TABLE_BLACKLIST = "A";
	private final static String TABLE_WHITELIST = "B";
	private final static String KEY_PHONE = "a";
	private final static String KEY_NAME = "b";

	private final static String TABLE_KEYWORDS = "C";
	private final static String KEY_KEYWORD = "a";
	private final static String SQL_KEYWORD = "CREATE TABLE IF NOT EXISTS " + TABLE_KEYWORDS + "("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_KEYWORD + " VARCHAR(20) NOT NULL"
			+ ");";

	public static synchronized DB_InfoList getInstance(Context context) {
		if (instance == null) {
			mContext = context.getApplicationContext();
			instance = new DB_InfoList(mContext);
		}
		return instance;
	}

	public DB_InfoList(Context context) {
		super(context, DB_NAME, null, VERSION_NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(getCreateSQL(TABLE_BLACKLIST));
		db.execSQL(getCreateSQL(TABLE_WHITELIST));
		db.execSQL(SQL_KEYWORD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exit " + TABLE_BLACKLIST);
		db.execSQL(getCreateSQL(TABLE_BLACKLIST));

		db.execSQL("drop table if exit " + TABLE_WHITELIST);
		db.execSQL(getCreateSQL(TABLE_WHITELIST));

		db.execSQL("drop table if exit " + TABLE_KEYWORDS);
		db.execSQL(SQL_KEYWORD);
	}

	private static String getCreateSQL(String tableName) {
		return "CREATE TABLE IF NOT EXISTS " + tableName + "("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_PHONE + " varchar(20) NOT NULL, "
				+ KEY_NAME + " varchar(20)" +
				");";
	}

	private List<PeopleInfo> getList(String listName) {
		List<PeopleInfo> result = new ArrayList<>();
		Cursor cursor = getReadableDatabase().rawQuery("select * from " + listName, null);
		while (cursor.moveToNext()) {
			final String phone = cursor.getString(cursor.getColumnIndex(KEY_PHONE));
			final String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
			PeopleInfo p = new PeopleInfo(name, phone);
			result.add(p);
		}
		if (cursor != null) {
			cursor.close();
		}
		return result;
	}

	private synchronized void addorUpdateList(String listName, PeopleInfo p) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			final String name = p.getName();
			final String phone = p.getPhone();
			ContentValues cv = new ContentValues();
			cv.put(KEY_NAME, name);
			cv.put(KEY_PHONE, phone);
			int effectRows = db.update(listName, cv, KEY_PHONE + " = ?", new String[]{phone});
			if (effectRows == 0) {
				long effect = db.insert(listName, null, cv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}

	}

	private void deleteList(String listName, PeopleInfo p) {
		final String phone = p.getPhone();
		SQLiteDatabase db = getWritableDatabase();
		int effectRows = db.delete(listName, KEY_PHONE + " = ?", new String[]{phone});
		if (db != null) {
			db.close();
		}
	}

	public void addorUpdateBlackList(PeopleInfo p) {
		addorUpdateList(TABLE_BLACKLIST, p);
	}

	public void addorUpdateWhiteList(PeopleInfo p) {
		addorUpdateList(TABLE_WHITELIST, p);
	}

	public List<PeopleInfo> getBlackList() {
		return getList(TABLE_BLACKLIST);
	}

	public List<PeopleInfo> getWhiteList() {
		return getList(TABLE_WHITELIST);
	}

	public void deleteBlackList(PeopleInfo p) {
		deleteList(TABLE_BLACKLIST, p);
	}

	public void deleteWhiteList(PeopleInfo p) {
		deleteList(TABLE_WHITELIST, p);
	}

	public void initKeyWordList(List<String> list) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_KEYWORDS, new String[]{KEY_KEYWORD}, null, null, null, null, null);
		while (cursor.moveToNext()) {
			final String keyword = cursor.getString(cursor.getColumnIndex(KEY_KEYWORD));
			list.add(keyword);
		}
		if (cursor != null) {
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
	}

	public void addKeyword(String keyword) {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = getWritableDatabase();
			ContentValues cv = new ContentValues();
			cv.put(KEY_KEYWORD, keyword);
			final int effect = db.update(TABLE_KEYWORDS, cv, KEY_KEYWORD + "=?", new String[]{keyword});
			if (effect == 0) {
				db.insert(TABLE_KEYWORDS, null, cv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
			if (cursor != null) {
				cursor.close();
			}
		}

	}

	public void deleteKeyword(String keyword) {
		SQLiteDatabase db = null;
		try {
			db = getWritableDatabase();
			final int effect = db.delete(TABLE_KEYWORDS, KEY_KEYWORD + "=?", new String[]{keyword});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}
}

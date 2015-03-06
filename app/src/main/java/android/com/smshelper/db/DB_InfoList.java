package android.com.smshelper.db;

import android.com.smshelper.entity.PeopleInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exit " + TABLE_BLACKLIST);
		db.execSQL(getCreateSQL(TABLE_BLACKLIST));
		db.execSQL("drop table if exit " + TABLE_WHITELIST);
		db.execSQL(getCreateSQL(TABLE_WHITELIST));
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
		return result;
	}

	private void addorUpdateList(String listName, PeopleInfo p) {
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
				Toast.makeText(mContext, "insert effectRows: " + effect, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(mContext, "update EffectRows: " + effectRows, Toast.LENGTH_LONG).show();
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
		if (effectRows > 0) {
			Toast.makeText(mContext, "effect Rows: " + effectRows, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(mContext, "effect Rows: " + effectRows, Toast.LENGTH_LONG).show();
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
}

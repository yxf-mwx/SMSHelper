package android.com.smshelper.database;

import android.com.smshelper.AppConstant;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yxf
 * @date 5/7/15
 * @time 9:38 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 智能过滤的本地数据库
 */
public class DB_Data_Smart {
	private Context mContext;
	private SQLiteDatabase mDB;
	private String TABLE_NAME = "sms_data";
	private String KEY_TOKEN = "token";
	private String KEY_SPAM = "spam";
	private String KEY_NON_SPAM = "non_spam";
	private static DB_Data_Smart mInstance;

	public synchronized static DB_Data_Smart getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DB_Data_Smart(context);
		}
		return mInstance;
	}

	public DB_Data_Smart(Context context) {
		mContext = context.getApplicationContext();
		mDB = SQLiteDatabase.openOrCreateDatabase(AppConstant.DB_BASEPATH + AppConstant.DB_SMART, null);
	}

	public Map<String, Integer> getSpamReflect() {
		Map<String, Integer> result = new HashMap<>();
		Cursor cursor = null;
		try {
			cursor = mDB.query(TABLE_NAME,
					new String[]{
							KEY_TOKEN,
							KEY_SPAM
					},
					null,
					null,
					null,
					null,
					null);
			while (cursor.moveToNext()) {
				final String token = cursor.getString(cursor.getColumnIndex(KEY_TOKEN));
				final int spam = cursor.getInt(cursor.getColumnIndex(KEY_SPAM));
				if (spam > 0) {
					result.put(token, spam);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}

	public Map<String, Integer> getNonSpamReflect() {
		Map<String, Integer> result = new HashMap<>();
		Cursor cursor = null;
		try {
			cursor = mDB.query(TABLE_NAME,
					new String[]{
							KEY_TOKEN,
							KEY_NON_SPAM
					},
					null,
					null,
					null,
					null,
					null);
			while (cursor.moveToNext()) {
				final String token = cursor.getString(cursor.getColumnIndex(KEY_TOKEN));
				final int non_spam = cursor.getInt(cursor.getColumnIndex(KEY_NON_SPAM));
				if (non_spam > 0) {
					result.put(token, non_spam);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
}

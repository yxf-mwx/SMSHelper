package android.com.smshelper.database;

import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsMessage;

/**
 * @author yxf
 * @date 15-4-26
 * @time 下午5:09
 * updater xxx
 * update  yy-MM-dd
 * @comment balabalabala
 */
public class Spam_DBH {
	private SQLiteDatabase mDb;

	public Spam_DBH(SQLiteDatabase db) {
		mDb = db;
	}

	public void add(SmsMessage message) {

	}

	public void close() {
		if (mDb != null) {
			mDb.close();
		}
	}
}

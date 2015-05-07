package android.com.smshelper.db;

import android.com.smshelper.AppConstant;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author yxf
 * @date 5/7/15
 * @time 9:38 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment balabalabala
 */
public class DB_Data_Smart {
	private SQLiteDatabase mDB;
	private String TABLE_NAME = "sms_data";
	private String KEY_SPAM = "spam";
	private String KEY_NON_SPAM = "non_spam";
	private static DB_Data_Smart mInstance;

	public synchronized static DB_Data_Smart getInstance() {
		if (mInstance != null) {
			mInstance = new DB_Data_Smart();
		}
		return mInstance;
	}

	public DB_Data_Smart() {
		mDB = SQLiteDatabase.openOrCreateDatabase(AppConstant.DB_BASEPATH + AppConstant.DB_SMART, null);
	}

}

package android.com.smshelper.db;

import android.com.smshelper.AppConstant;
import android.com.smshelper.entity.NameValue;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by admin on 15-1-21.
 */
public class DB_Dm_Mobile {
	private SQLiteDatabase mDb;
	private static DB_Dm_Mobile instance;
	private static String MOBILE_NUMBER = "MobileNumber";
	private static String MOBILE_AREA = "MobileArea";
	private static String MOBILE_TYPE = "MobileType";
	private static String TABLE_DM = "Dm_Mobile";

	DB_Dm_Mobile() {
		mDb = SQLiteDatabase.openOrCreateDatabase(AppConstant.DB_BASEPATH + AppConstant.DB_DM_MOBILE, null);
	}

	public synchronized static DB_Dm_Mobile getInstance() {
		if (instance == null) {
			instance = new DB_Dm_Mobile();
		}
		return instance;
	}

	public NameValue<String, String> getMobileArea(String phoneNumber) {
		Cursor cursor = mDb.query(
				TABLE_DM,
				new String[]{
						MOBILE_AREA,
						MOBILE_TYPE
				},
				MOBILE_NUMBER + " = ?",
				new String[]{phoneNumber},
				null,
				null,
				null
		);

		while (cursor.moveToNext()) {
			String mobile_area = cursor.getString(cursor.getColumnIndex(MOBILE_AREA));
			String mobile_type = cursor.getString(cursor.getColumnIndex(MOBILE_TYPE));
			return new NameValue<String, String>(mobile_area, mobile_type);
		}
		if (cursor != null) {
			cursor.close();
		}
		return null;
	}
}

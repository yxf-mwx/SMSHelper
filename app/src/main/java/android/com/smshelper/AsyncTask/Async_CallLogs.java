package android.com.smshelper.AsyncTask;

import android.com.smshelper.db.DB_CallLogs_Cache;
import android.com.smshelper.db.DB_Dm_Mobile;
import android.com.smshelper.entity.CallLogs;
import android.com.smshelper.entity.NameValue;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 15-1-21.
 */
public class Async_CallLogs implements Runnable {
	private Context mContext;

	public Async_CallLogs(Context context) {
		mContext = context;
	}

	@Override
	public void run() {
		final long lastUpdateCacheTime = DB_CallLogs_Cache.getInstance(mContext).getLastUpdateTime();
		Log.d("Async_CallLogs", String.valueOf(lastUpdateCacheTime));
		copyToCache(lastUpdateCacheTime);
	}

	private void copyToCache(long lastUpdateTime) {

		ContentResolver resolver = mContext.getContentResolver();
		Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI,
				new String[]{
						CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,
						CallLog.Calls.TYPE, CallLog.Calls.DATE
				},
				CallLog.Calls.DATE + " > " + lastUpdateTime,
				null,
				"date ASC");
		CallLogs log = new CallLogs("", "", 0L, 1, "", "");
		while (cursor.moveToNext()) {
			String phone = "";
			String name = "";
			long date = 0L;
			int type = 1;
			String mobileArea = "";
			String mobileType = "";
			phone = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
			name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
			date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
			type = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));
			Pattern p = Pattern.compile("\\d{7}");
			Matcher m = p.matcher(phone);
			if (m.find()) {
				String suffix_phone = m.group(0);
				NameValue<String, String> info = DB_Dm_Mobile.getInstance().getMobileArea(suffix_phone);
				mobileArea = info.getKey();
				mobileType = info.getValue();
			}
			log.setPhone(phone);
			log.setName(name);
			log.setDate(date);
			log.setType(type);
			log.setNumArea(mobileArea);
			log.setNumType(mobileType);
			DB_CallLogs_Cache.getInstance(mContext).insert(log);
		}
	}
}


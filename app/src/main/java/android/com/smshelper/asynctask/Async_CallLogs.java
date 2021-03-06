package android.com.smshelper.asynctask;

import android.com.smshelper.AppConstant;
import android.com.smshelper.database.DB_CallLogs_Cache;
import android.com.smshelper.database.DB_Dm_Mobile;
import android.com.smshelper.entity.CallLogs;
import android.com.smshelper.entity.NameValue;
import android.com.smshelper.interfac.AsyncCallBack;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.CallLog;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 15-1-21.
 */
public class Async_CallLogs implements Runnable {
	private Context mContext;
	private AsyncCallBack mListener;

	public Async_CallLogs(Context context, AsyncCallBack listener) {
		mContext = context;
		mListener = listener;
	}

	@Override
	public void run() {
		//先检查字典是否已经就绪
		copyDmMobile();

		//拿最后通话的时间
		final long lastUpdateCacheTime = DB_CallLogs_Cache.getInstance(mContext).getLastUpdateTime();

		//根据最后通话的时间选择copy到cache中
		copyToCache(lastUpdateCacheTime);
		if (mListener != null) {
			mListener.onFinished();
		}
		mListener = null;
		mContext = null;
	}

	//把通话记录的从系统数据库copy到app本地数据缓存目录下
	private void copyToCache(long lastUpdateTime) {
		Set<String> map = new HashSet<>();
		List<CallLogs> insertList = new ArrayList<>();
		ContentResolver resolver = mContext.getContentResolver();
		Cursor cursor = resolver.query(
				CallLog.Calls.CONTENT_URI,
				new String[]{
						CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,
						CallLog.Calls.TYPE, CallLog.Calls.DATE,
						CallLog.Calls.CACHED_NORMALIZED_NUMBER
				},
				CallLog.Calls.DATE + ">?",
				new String[]{String.valueOf(lastUpdateTime)},
				"date DESC"
		);
		while (cursor.moveToNext()) {
			CallLogs log = new CallLogs("", "", 0L, 1, "", "");
			String phone = "";
			String name = "";
			long date = 0L;
			int type = 1;
			String mobileArea = "";
			String mobileType = "";
			phone = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
			if (map.contains(phone)) {
				continue;
			} else {
				map.add(phone);
			}
			name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
			date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
			type = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));
			Pattern p = Pattern.compile("\\d{7}");
			Matcher m = p.matcher(phone);
			if (m.find()) {
				String suffix_phone = m.group(0);
				if (TextUtils.isEmpty(suffix_phone)) {
					mobileArea = "";
					mobileType = "";
				} else {
					NameValue<String, String> info = DB_Dm_Mobile.getInstance().getMobileArea(suffix_phone);
					mobileArea = info.getKey();
					mobileType = info.getValue();
				}
			}
			log.setPhone(phone);
			log.setName(name);
			log.setDate(date);
			log.setType(type);
			log.setNumArea(mobileArea);
			log.setNumType(mobileType);
			insertList.add(log);
		}
		DB_CallLogs_Cache.getInstance(mContext).insertAll(insertList);
	}

	//根据是否已经存在号码归属地数据库，决定是否需要copy
	private void copyDmMobile() {
		SharedPreferences sp = mContext.getSharedPreferences(AppConstant.SP_FILENAME, Context.MODE_PRIVATE);
		boolean isFirstInited = sp.getBoolean(AppConstant.SP_INIT_FIRST, false);
		if (isFirstInited) {
			//不是第一次打开
		} else {
			//第一次打开
			copy();
		}
	}

	//将数据库从app拷贝到数据目录下
	private void copy() {
		boolean copyResult = true;
		final String path = AppConstant.DB_BASEPATH + AppConstant.DB_DM_MOBILE;
		File dir = new File(AppConstant.DB_BASEPATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File f = new File(path);
		InputStream is = null;
		OutputStream os = null;
		try {
			is = mContext.getAssets().open("a.db");
			os = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int count;
			while ((count = is.read(buffer)) > 0) {
				os.write(buffer, 0, count);
			}
		} catch (Exception e) {
			e.printStackTrace();
			copyResult = false;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		SharedPreferences mSpf = mContext.getSharedPreferences(AppConstant.SP_FILENAME, Context.MODE_PRIVATE);
		if (copyResult) {
			SharedPreferences.Editor editor = mSpf.edit();
			editor.putBoolean(AppConstant.SP_INIT_FIRST, true);
			editor.commit();
		}
	}
}
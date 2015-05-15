package android.com.smshelper.database;

import android.com.smshelper.AppConstant;
import android.com.smshelper.entity.TokenWord;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
		final File smartDataFile = new File(AppConstant.DB_BASEPATH + AppConstant.DB_SMART);
		if (!smartDataFile.exists()) {
			copySmartData(smartDataFile);
		}
		mDB = SQLiteDatabase.openOrCreateDatabase(smartDataFile.getPath(), null);
	}


	public List<TokenWord> getTokenInformation(List<String> wordList) {
		List<TokenWord> tokenWordList = new ArrayList<>();
		for (String s : wordList) {
			Cursor cursor = null;
			try {
				cursor = mDB.query(TABLE_NAME,
						new String[]{
								KEY_TOKEN,
								KEY_SPAM,
								KEY_NON_SPAM,
						},
						KEY_TOKEN + "=?",
						new String[]{s},
						null,
						null,
						null);
				if (cursor.getCount() == 0) {
					tokenWordList.add(new TokenWord(s, 0, 0));
				} else {
					final int spam = cursor.getInt(cursor.getColumnIndex(KEY_SPAM));
					final int non_spam = cursor.getInt(cursor.getColumnIndex(KEY_NON_SPAM));
					tokenWordList.add(new TokenWord(s, spam, non_spam));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
		return tokenWordList;
	}

	private void copySmartData(File dataFile) {
		new File(AppConstant.DB_BASEPATH).mkdir();
		InputStream is = null;
		OutputStream os = null;
		try {
			is = mContext.getAssets().open("data.db");
			os = new FileOutputStream(dataFile);
			byte[] buffer = new byte[1024];
			int count;
			while ((count = is.read(buffer)) > 0) {
				os.write(buffer, 0, count);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	}
}

package android.com.smshelper.db;

import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.entity.SMSEntity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxf on 15-1-9.
 * 短信助手的主要数据库类
 */
public class DB_InfoList extends SQLiteOpenHelper {
	public static DB_InfoList instance = null;
	private static Context mContext;
	private final static int VERSION_NAME = 1;
	private final static String DB_NAME = "INFO";

	private final static String KEY_PHONE = "a";
	private final static String KEY_NAME = "b";

	private final static String SQL_KEYWORD = "CREATE TABLE IF NOT EXISTS " + Keyword.TABLE_Name + "("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Keyword.KEY_KEYWORD + " VARCHAR(20) NOT NULL"
			+ ");";
	private final static String SQL_SPAM = "CREATE TABLE IF NOT EXISTS " + SpamList.TABLE_NAME + "("
			+ SpamList.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ SpamList.KEY_ADDRESS + " VARCHAR(20) NOT NULL, "
			+ SpamList.KEY_BODY + " TEXT NOT NULL, "
			+ SpamList.KEY_DATE + " LONG NOT NULL, "
			+ SpamList.KEY_DATESENT + " LONG NOT NULL,"
			+ SpamList.KEY_ERROR_CODE + " INT, "
			+ SpamList.KEY_LOCKED + " INT, "
			+ SpamList.KEY_PROTOCOL + " INT, "
			+ SpamList.KEY_READ + " INT, "
			+ SpamList.KEY_REPLY_PATH_PRESENT + " BOOLEAN, "
			+ SpamList.KEY_SEEN + " INT, "
			+ SpamList.KEY_SERVICECENTER + " TEXT, "
			+ SpamList.KEY_STATUS + " INTEGER, "
			+ SpamList.KEY_SUBJECT + " TEXT, "
			+ SpamList.KEY_THREADID + " INT, "
			+ SpamList.KEY_TYPE + " INTEGER "
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
		db.execSQL(getCreateSQL(BlackList.TABLE_Name));
		db.execSQL(getCreateSQL(WhiteList.TABLE_Name));
		db.execSQL(SQL_KEYWORD);
		db.execSQL(SQL_SPAM);
		System.out.println(SQL_SPAM);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//黑名单列表
		db.execSQL("drop table if exist " + BlackList.TABLE_Name);
		db.execSQL(getCreateSQL(BlackList.TABLE_Name));
		//白名单列表
		db.execSQL("drop table if exist " + WhiteList.TABLE_Name);
		db.execSQL(getCreateSQL(WhiteList.TABLE_Name));
		//关键字列表
		db.execSQL("drop table if exist " + Keyword.TABLE_Name);
		db.execSQL(SQL_KEYWORD);
		//垃圾短信列表
		db.execSQL("drop table if exit " + SpamList.TABLE_NAME);
		db.execSQL(SQL_SPAM);
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
		addorUpdateList(BlackList.TABLE_Name, p);
	}

	public void addorUpdateWhiteList(PeopleInfo p) {
		addorUpdateList(WhiteList.TABLE_Name, p);
	}

	public List<PeopleInfo> getBlackList() {
		return getList(BlackList.TABLE_Name);
	}

	public List<PeopleInfo> getWhiteList() {
		return getList(WhiteList.TABLE_Name);
	}

	public void deleteBlackList(PeopleInfo p) {
		deleteList(BlackList.TABLE_Name, p);
	}

	public void deleteWhiteList(PeopleInfo p) {
		deleteList(WhiteList.TABLE_Name, p);
	}

	public void initKeyWordList(List<String> list) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(Keyword.TABLE_Name, new String[]{Keyword.KEY_KEYWORD}, null, null, null, null,
				null);
		while (cursor.moveToNext()) {
			final String keyword = cursor.getString(cursor.getColumnIndex(Keyword.KEY_KEYWORD));
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
			cv.put(Keyword.KEY_KEYWORD, keyword);
			final int effect = db.update(Keyword.TABLE_Name, cv, Keyword.KEY_KEYWORD + "=?",
					new String[]{keyword});
			if (effect == 0) {
				db.insert(Keyword.TABLE_Name, null, cv);
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
			final int effect = db.delete(Keyword.TABLE_Name, Keyword.KEY_KEYWORD + "=?", new String[]{keyword});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	public synchronized void addSpamSMS(SMSEntity msg) {
		SQLiteDatabase db = null;
		try {
			db = getWritableDatabase();
			final long code = db.insert(SpamList.TABLE_NAME, null, getCVFromSMS(msg));
			System.out.println("spam insert code: " + code);
		} catch (Exception e) {
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	private ContentValues getCVFromSMS(SMSEntity msg) {
		ContentValues cv = new ContentValues();
		cv.put(SpamList.KEY_ADDRESS, msg.getAddress());
		cv.put(SpamList.KEY_BODY, msg.getBody());
		cv.put(SpamList.KEY_DATE, msg.getDate());
		cv.put(SpamList.KEY_DATESENT, msg.getDateSent());
		cv.put(SpamList.KEY_ERROR_CODE, msg.getErrorCode());
//		cv.put(SpamList.KEY_LOCKED);
		cv.put(SpamList.KEY_PROTOCOL, msg.getProtocol());
		cv.put(SpamList.KEY_READ, msg.isRead() ? 1 : 0);
		cv.put(SpamList.KEY_REPLY_PATH_PRESENT, msg.isReplyPathPresent() ? 1 : 0);
		cv.put(SpamList.KEY_SEEN, msg.isSeen() ? 1 : 0);
		cv.put(SpamList.KEY_SERVICECENTER, msg.getServiceCenter());
//		cv.put(SpamList.KEY_STATUS,msg.get);
		cv.put(SpamList.KEY_SUBJECT, msg.getSubject());
		cv.put(SpamList.KEY_THREADID, msg.getThreadId());
//		cv.put(SpamList.KEY_TYPE,msg.get);
		return cv;
	}

	public List<SMSEntity> initSpamList() {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			cursor = db.query(SpamList.TABLE_NAME, SpamList.projection, null, null, null, null, null);
			return getSpamListFromCursor(cursor);
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
		return null;
	}

	private List<SMSEntity> getSpamListFromCursor(Cursor cursor) {
		List<SMSEntity> result = new ArrayList<>();
		while (cursor.moveToNext()) {
			final String address = cursor.getString(cursor.getColumnIndex(SpamList.KEY_ADDRESS));
			final String body = cursor.getString(cursor.getColumnIndex(SpamList.KEY_BODY));
			final long date = cursor.getLong(cursor.getColumnIndex(SpamList.KEY_DATE));
			final long dateSent = cursor.getLong(cursor.getColumnIndex(SpamList.KEY_DATESENT));
			final int errorCode = cursor.getInt(cursor.getColumnIndex(SpamList.KEY_ERROR_CODE));
			final int protocol = cursor.getInt(cursor.getColumnIndex(SpamList.KEY_PROTOCOL));
			final boolean read = cursor.getInt(cursor.getColumnIndex(SpamList.KEY_READ)) != 0;
			final boolean seen = cursor.getInt(cursor.getColumnIndex(SpamList.KEY_SEEN)) != 0;
			final String subject = cursor.getString(cursor.getColumnIndex(SpamList.KEY_SUBJECT));
			final boolean replyPathPresent =
					cursor.getInt(cursor.getColumnIndex(SpamList.KEY_REPLY_PATH_PRESENT)) != 0;
			final long threadId = cursor.getLong(cursor.getColumnIndex(SpamList.KEY_THREADID));
			final String serviceCenter = cursor.getString(cursor.getColumnIndex(SpamList.KEY_SERVICECENTER));
			SMSEntity entity = new SMSEntity(errorCode, body, address, date, dateSent, protocol, read, seen, subject,
					replyPathPresent, threadId, serviceCenter);
			result.add(entity);
		}
		return result;
	}

	public static final class WhiteList {
		public final static String TABLE_Name = "B";
		private final static String KEY_PHONE = "a";
		private final static String KEY_NAME = "b";
	}

	public static final class BlackList {
		public final static String TABLE_Name = "A";
		private final static String KEY_PHONE = "a";
		private final static String KEY_NAME = "b";
	}

	public static final class Keyword {
		public final static String TABLE_Name = "C";
		private final static String KEY_KEYWORD = "a";
	}

	public static final class SpamList {
		public final static String TABLE_NAME = "D";
		public final static String KEY_ID = "_id";
		public final static String KEY_THREADID = "thread_id";
		public final static String KEY_ADDRESS = "address";
		public final static String KEY_DATE = "date";
		public final static String KEY_DATESENT = "date_sent";
		public final static String KEY_PROTOCOL = "protocol";
		public final static String KEY_READ = "read";
		public final static String KEY_STATUS = "status";
		public final static String KEY_TYPE = "type";
		public final static String KEY_REPLY_PATH_PRESENT = "reply_path_present";
		public final static String KEY_SUBJECT = "subject";
		public final static String KEY_BODY = "body";
		public final static String KEY_SERVICECENTER = "service_center";
		public final static String KEY_LOCKED = "locked";
		public final static String KEY_ERROR_CODE = "error_code";
		public final static String KEY_SEEN = "seen";

		/**
		 * TP-Status: no status received.
		 */
		public static final int STATUS_NONE = -1;
		/**
		 * TP-Status: complete.
		 */
		public static final int STATUS_COMPLETE = 0;
		/**
		 * TP-Status: pending.
		 */
		public static final int STATUS_PENDING = 32;
		/**
		 * TP-Status: failed.
		 */
		public static final int STATUS_FAILED = 64;

		public static final String[] projection = new String[]{
				KEY_ID,
				KEY_THREADID,
				KEY_ADDRESS,
				KEY_DATE,
				KEY_DATESENT,
				KEY_PROTOCOL,
				KEY_READ,
				KEY_STATUS,
				KEY_TYPE,
				KEY_REPLY_PATH_PRESENT,
				KEY_SUBJECT,
				KEY_BODY,
				KEY_SERVICECENTER,
				KEY_LOCKED,
				KEY_ERROR_CODE,
				KEY_SEEN
		};
	}

}

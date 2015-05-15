package android.com.smshelper.asynctask;

import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.entity.SMSEntity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yxf
 * @date 5/2/15
 * @time 10:24 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 垃圾短信导入
 */
public class Async_Spam_Import extends AsyncTask<Void, Void, List<SMSEntity>> {
	private Context mContext;
	private List<PeopleInfo> mInfos;
	private CallBack mCallBack;

	public Async_Spam_Import(Context context, List<PeopleInfo> infos, CallBack callBack) {
		mContext = context;
		mInfos = infos;
		mCallBack = callBack;
	}

	@Override
	protected List<SMSEntity> doInBackground(Void... params) {
		ContentResolver cr = mContext.getContentResolver();
		Cursor cursor = null;
		try {
			if (mInfos != null) {
				List<SMSEntity> list = new ArrayList<>();
				for (PeopleInfo info : mInfos) {
					cursor = cr.query(
							Uri.parse("content://sms"),
							new String[]{
									Telephony.Sms.ADDRESS,
									Telephony.Sms.BODY,
									Telephony.Sms.ERROR_CODE,
									Telephony.Sms.DATE,
									Telephony.Sms.DATE_SENT,
									Telephony.Sms.PROTOCOL,
									Telephony.Sms.READ,
									Telephony.Sms.SEEN,
									Telephony.Sms.SUBJECT,
									Telephony.Sms.REPLY_PATH_PRESENT,
									Telephony.Sms.THREAD_ID,
									Telephony.Sms.SERVICE_CENTER,

							},
							Telephony.Sms.ADDRESS + "=?",
							new String[]{
									info.getPhone()
							},
							null
					);
					list.addAll(getEntityFromCursor(cursor));
					cr.delete(
							Uri.parse("content://sms"),
							Telephony.Sms.ADDRESS + "=? and " + Telephony.Sms.TYPE + "=?",
							new String[]{
									info.getPhone(),
									String.valueOf(Telephony.Sms.MESSAGE_TYPE_INBOX)
							});
				}
				return list;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<SMSEntity> list) {
		super.onPostExecute(list);
		if (mCallBack != null) {
			mCallBack.callBack(list);
		}
	}

	private List<SMSEntity> getEntityFromCursor(Cursor cursor) {
		List<SMSEntity> list = new ArrayList<>();
		while (cursor.moveToNext()) {
			final int errorCode = cursor.getInt(cursor.getColumnIndex(Telephony.Sms.ERROR_CODE));
			final String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
			final String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
			final long date = cursor.getLong(cursor.getColumnIndex(Telephony.Sms.DATE));
			final long dateSent = cursor.getLong(cursor.getColumnIndex(Telephony.Sms.DATE_SENT));
			final int protocol = cursor.getInt(cursor.getColumnIndex(Telephony.Sms.PROTOCOL));
			final boolean read = cursor.getInt(cursor.getColumnIndex(Telephony.Sms.READ)) != 0;
			final boolean seen = cursor.getInt(cursor.getColumnIndex(Telephony.Sms.SEEN)) != 0;
			final String subject = cursor.getString(cursor.getColumnIndex(Telephony.Sms.SUBJECT));
			final boolean replyPathPresent = cursor.getInt(cursor.getColumnIndex(Telephony.Sms.REPLY_PATH_PRESENT))
					!= 0;
			final long threadId = cursor.getLong(cursor.getColumnIndex(Telephony.Sms.THREAD_ID));
			final String serviceCenter = cursor.getString(cursor.getColumnIndex(Telephony.Sms.SERVICE_CENTER));
			SMSEntity entity = new SMSEntity(errorCode, body, address, date, dateSent, protocol, read, seen, subject,
					replyPathPresent, threadId, serviceCenter);
			list.add(entity);
		}
		return list;
	}

	public interface CallBack {
		public void callBack(List<SMSEntity> list);
	}
}

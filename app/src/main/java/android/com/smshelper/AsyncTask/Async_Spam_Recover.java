package android.com.smshelper.AsyncTask;

import android.com.smshelper.entity.SMSEntity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Telephony;

import java.util.List;

/**
 * @author yxf
 * @date 5/2/15
 * @time 9:28 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 垃圾短信恢复的异步类
 */
public class Async_Spam_Recover extends AsyncTask<Void, Void, Void> {
	private Context mContext;
	private List<SMSEntity> mListRecover;

	public Async_Spam_Recover(Context context, List<SMSEntity> listRecover) {
		mContext = context;
		mListRecover = listRecover;
	}

	@Override
	protected Void doInBackground(Void... params) {
		ContentResolver cr = mContext.getContentResolver();
		for (SMSEntity entity : mListRecover) {
			cr.insert(Uri.parse("content://sms"), getContentValueFromSMS(entity));
		}
		return null;
	}

	private ContentValues getContentValueFromSMS(SMSEntity entity) {
		ContentValues cv = new ContentValues();
		cv.put(Telephony.Sms.ADDRESS, entity.getAddress());
		cv.put(Telephony.Sms.BODY, entity.getBody());
		cv.put(Telephony.Sms.DATE, entity.getDate());
		cv.put(Telephony.Sms.DATE_SENT, entity.getDateSent());
		cv.put(Telephony.Sms.READ, entity.isRead() ? 1 : 0);
		cv.put(Telephony.Sms.SEEN, entity.isSeen() ? 1 : 0);
		cv.put(Telephony.Sms.SERVICE_CENTER, entity.getServiceCenter());
		cv.put(Telephony.Sms.ERROR_CODE, entity.getErrorCode());
		cv.put(Telephony.Sms.PROTOCOL, entity.getProtocol());
		cv.put(Telephony.Sms.REPLY_PATH_PRESENT, entity.isReplyPathPresent() ? 1 : 0);
		cv.put(Telephony.Sms.SUBJECT, entity.getSubject());
		cv.put(Telephony.Sms.TYPE, Telephony.Sms.MESSAGE_TYPE_INBOX);
		return cv;
	}
}

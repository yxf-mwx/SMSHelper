package android.com.smshelper.AsyncTask;

import android.com.smshelper.entity.ListItemSMS;
import android.com.smshelper.interfac.OnReadSMSFinished;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxf on 15-4-8.
 */
public class Async_SMS extends AsyncTask<Void, Void, Void> {
	private OnReadSMSFinished mListener;
	private Context mContext;
	private List<ListItemSMS> mList;

	public Async_SMS(Context context, OnReadSMSFinished listener) {
		mContext = context;
		mListener = listener;
		mList = new ArrayList<>();
	}

	@Override
	protected Void doInBackground(Void... params) {
		ContentResolver cr = mContext.getContentResolver();
		String[] projection = new String[]{
				Telephony.Sms.ADDRESS,
				Telephony.Sms.BODY,
				Telephony.Sms.DATE,
				Telephony.Sms.PERSON
		};
		Cursor cursor = cr.query(Telephony.Sms.Inbox.CONTENT_URI, projection, null, null,
				Telephony.Sms.DEFAULT_SORT_ORDER);
		while (cursor.moveToNext()) {
			final String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
			final String person = cursor.getString(cursor.getColumnIndex(Telephony.Sms.PERSON));
			final String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
			final long date = cursor.getLong(cursor.getColumnIndex(Telephony.Sms.DATE));
			ListItemSMS item = new ListItemSMS();
			item.setAddress(address);
			item.setBody(body);
			item.setDate(date);
			item.setPersion(person);
			mList.add(item);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		if (mListener != null) {
			mListener.onReadSMSFinished(mList);
		}
	}
}

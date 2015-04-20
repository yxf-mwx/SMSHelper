package android.com.smshelper.AsyncTask;

import android.com.smshelper.entity.ListItemSMS;
import android.com.smshelper.interfac.OnReadSMSFinished;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yxf on 15-4-8.
 */
public class Async_SMS extends AsyncTask<Void, Void, Void> {
	private OnReadSMSFinished mListener;
	private Context mContext;
	private List<ListItemSMS> mList;
	private Map<String, ListItemSMS> mMap;

	public Async_SMS(Context context, OnReadSMSFinished listener) {
		mContext = context;
		mListener = listener;
		mList = new ArrayList<>();
		mMap = new HashMap<>();
	}

	//???????map????????
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
			mMap.put(address, item);
		}

		for (Map.Entry<String, ListItemSMS> entry : mMap.entrySet()) {
			ListItemSMS item = entry.getValue();
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

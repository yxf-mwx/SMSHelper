package android.com.smshelper.AsyncTask;

import android.com.smshelper.entity.Contact;
import android.com.smshelper.interfac.OnReadContactFinished;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxf on 15-3-8.
 */
public class Async_Contacts extends AsyncTask<Void, Void, List<Contact>> {
	private Context mContext;
	private OnReadContactFinished mCallBack;

	public Async_Contacts(Context context, OnReadContactFinished callBack) {
		mContext = context;
		mCallBack = callBack;
	}

	@Override
	protected List<Contact> doInBackground(Void... params) {
		List<Contact> result = new ArrayList<>();
		ContentResolver cr = mContext.getContentResolver();
		final String[] mContactsProjection = new String[]{
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
				ContactsContract.CommonDataKinds.Phone.NUMBER,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
		};
		Cursor cursor = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, mContactsProjection, null, null, null);
		while (cursor.moveToNext()) {
			final String phone = cursor.getString(1);
			final String nick = cursor.getString(2);
			Contact contact = new Contact(nick, phone);
			result.add(contact);
		}
		cursor.close();
		return result;
	}

	@Override
	protected void onPostExecute(List<Contact> list) {
		super.onPostExecute(list);
		if (mCallBack != null) {
			mCallBack.onFinish(list);
		}
	}
}

package android.com.smshelper.AsyncTask;

import android.com.smshelper.entity.Contact;
import android.com.smshelper.interfac.OnReadContactFinished;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
		Uri contentUri = ContactsContract.Contacts.CONTENT_URI;
		Cursor cursor = cr.query(contentUri, null, null, null, null);
		String nick = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
		String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

		Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		Cursor phone = cr.query(phoneUri, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
				new String[]{contactId}, null);
		while (phone.moveToNext()) {
			String phoneNum = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			Contact contact = new Contact(nick, phoneNum);
			result.add(contact);
		}
		phone.close();
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

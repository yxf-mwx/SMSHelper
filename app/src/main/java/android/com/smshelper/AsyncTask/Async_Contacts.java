package android.com.smshelper.AsyncTask;

import android.com.smshelper.entity.Contact;
import android.com.smshelper.interfac.OnReadContactFinished;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		Map<String, String> map = new HashMap();
		ContentResolver cr = mContext.getContentResolver();
		final String[] mContactsProjection = new String[]{
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
				ContactsContract.CommonDataKinds.Phone.NUMBER,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,

		};
		Cursor cursor = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, mContactsProjection, null, null, null);
		while (cursor.moveToNext()) {
			final String phone = cursor.getString(1);
			final String nick = cursor.getString(2);
			map.put(phone, nick);
		}
		List<Contact> result = new ArrayList<>();
		Set<Map.Entry<String, String>> entitySet = map.entrySet();
		Iterator<Map.Entry<String, String>> it = entitySet.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> mapEntry = it.next();
			String nick = mapEntry.getValue();
			String phone = mapEntry.getKey();
			Contact contact = new Contact(nick, phone);
			result.add(contact);
		}
		cursor.close();
		Collections.sort(result, new PinYinComparator());
		return result;
	}

	@Override
	protected void onPostExecute(List<Contact> list) {
		super.onPostExecute(list);
		if (mCallBack != null) {
			mCallBack.onFinish(list);
		}
	}

	private class PinYinComparator implements Comparator<Contact> {
		@Override
		public int compare(Contact lhs, Contact rhs) {
			try {
				return generatePinYinString(lhs.getNick()).compareTo(generatePinYinString(rhs.getNick()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}

		private String generatePinYinString(String s) {
			StringBuffer sb = new StringBuffer();
			if (!TextUtils.isEmpty(s)) {
				for (int i = 0; i < s.length(); i++) {
					char c = s.charAt(i);
					String[] array = PinyinHelper.toHanyuPinyinStringArray(c);
					if (null != array && array.length > 0) {
						sb.append(array[0]);
					}
				}
			}
			return sb.toString();
		}

		@Override
		public boolean equals(Object object) {
			return false;
		}
	}
}

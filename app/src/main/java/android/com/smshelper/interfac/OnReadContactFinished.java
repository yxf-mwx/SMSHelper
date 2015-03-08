package android.com.smshelper.interfac;

import android.com.smshelper.entity.Contact;

import java.util.List;

/**
 * Created by yxf on 15-3-8.
 */
public interface OnReadContactFinished {
	public void onFinish(List<Contact> list);
}

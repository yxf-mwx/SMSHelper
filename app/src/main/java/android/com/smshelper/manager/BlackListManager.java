package android.com.smshelper.manager;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15-1-9.
 */
public class BlackListManager {
	private static BlackListManager instance = null;

	private List<String> mList = null;

	public BlackListManager() {
		mList = new ArrayList<>();
	}

	public static synchronized BlackListManager getInstance() {
		if (instance == null) {
			instance = new BlackListManager();
		}
		return instance;
	}

	public void addInfo(String phoneNumber) {
		if (!TextUtils.isEmpty(phoneNumber)) {
			if (!phoneNumber.contains(phoneNumber)) {
				mList.add(phoneNumber);
			}
		}
	}

}

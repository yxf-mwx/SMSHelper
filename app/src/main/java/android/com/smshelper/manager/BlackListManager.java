package android.com.smshelper.manager;

import android.com.smshelper.entity.PeopleInfo;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15-1-9.
 */
public class BlackListManager {
	private static BlackListManager instance = null;

	private List<PeopleInfo> mList = null;

	public BlackListManager() {
		mList = new ArrayList<>();
		mList.add(new PeopleInfo("Tom", "123"));
		mList.add(new PeopleInfo("Kom", "456"));
		mList.add(new PeopleInfo("Rom", "789"));
	}

	public static synchronized BlackListManager getInstance() {
		if (instance == null) {
			instance = new BlackListManager();
		}
		return instance;
	}

	public void addorUpdateInfo(PeopleInfo info) {
		final String phone = info.getPhone();
		final String name = info.getName();

		if (TextUtils.isEmpty(phone)) {
			return;
		}

		for (PeopleInfo i : mList) {
			final String p = i.getPhone();
			if (p.equals(phone)) {
				i.setName(name);
				return;
			}
		}

		mList.add(info);
	}

	public List<PeopleInfo> getBlackList() {
		return mList;
	}

	public void delInfo(int position) {
		if (position < mList.size() && position >= 0) {
			mList.remove(position);
		}
	}
}

package android.com.smshelper.manager;

import android.com.smshelper.entity.PeopleInfo;

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

	public void addInfo(PeopleInfo info) {
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

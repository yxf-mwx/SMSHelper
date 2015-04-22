package android.com.smshelper.manager;

import android.com.smshelper.db.db_base.DB_Info;
import android.com.smshelper.entity.PeopleInfo;
import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15-1-9.
 */
public class BlackListManager {
	private static BlackListManager instance = null;
	private Context mContext;
	private List<PeopleInfo> mList = null;

	public BlackListManager(Context context) {
		mContext = context.getApplicationContext();
		mList = new ArrayList<>();
		List<PeopleInfo> dbList = DB_Info.getInstance(mContext).getBlackList();
		for (PeopleInfo p : dbList) {
			mList.add(p);
		}
		dbList.clear();
		dbList = null;
	}

	public static synchronized BlackListManager getInstance(Context context) {
		if (instance == null) {
			instance = new BlackListManager(context);
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
				DB_Info.getInstance(mContext).addorUpdateBlackList(info);
				return;
			}
		}
		DB_Info.getInstance(mContext).addorUpdateBlackList(info);
		mList.add(info);
	}

	public void addorUpdateInfoList(List<PeopleInfo> infoList) {
		for (PeopleInfo info : infoList) {
			addorUpdateInfo(info);
		}
	}

	public List<PeopleInfo> getBlackList() {
		return mList;
	}

	public void delInfo(int position) {
		PeopleInfo info = null;
		if (position < mList.size() && position >= 0) {
			info = mList.remove(position);
		}
		if (info != null) {
			DB_Info.getInstance(mContext).deleteBlackList(info);
		}
	}
}

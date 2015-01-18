package android.com.smshelper.manager;

import android.com.smshelper.db.InfoList_DB;
import android.com.smshelper.entity.PeopleInfo;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by admin on 15-1-18.
 */
public class WhiteListManager {
	private static WhiteListManager instance;
	private Context mContext;
	private List<PeopleInfo> mList;

	public WhiteListManager(Context context) {
		mContext = context.getApplicationContext();
		mList = InfoList_DB.getInstance(context).getWhiteList();
	}

	public synchronized static WhiteListManager getInstance(Context context) {
		if (instance == null) {
			instance = new WhiteListManager(context);
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
				InfoList_DB.getInstance(mContext).addorUpdateWhiteList(info);
				return;
			}
		}
		InfoList_DB.getInstance(mContext).addorUpdateWhiteList(info);
		mList.add(info);
	}

	public List<PeopleInfo> getWhiteList() {
		return mList;
	}

	public void delInfo(int position) {
		PeopleInfo info = null;
		if (position < mList.size() && position >= 0) {
			info = mList.remove(position);
		}
		if (info != null) {
			InfoList_DB.getInstance(mContext).deleteWhiteList(info);
		}
	}
}

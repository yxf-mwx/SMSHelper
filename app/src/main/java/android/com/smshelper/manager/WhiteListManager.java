package android.com.smshelper.manager;

import android.com.smshelper.database.DB_InfoList;
import android.com.smshelper.entity.PeopleInfo;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by admin on 15-1-18.
 * 白名单管理类
 */
public class WhiteListManager {
	private static WhiteListManager instance;
	private Context mContext;
	private List<PeopleInfo> mList;

	public WhiteListManager(Context context) {
		mContext = context.getApplicationContext();
		mList = DB_InfoList.getInstance(context).getWhiteList();
	}

	public synchronized static WhiteListManager getInstance(Context context) {
		if (instance == null) {
			instance = new WhiteListManager(context);
		}
		return instance;
	}

	public void addorUpdateInfo(final PeopleInfo info) {
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
		new Thread(new Runnable() {
			@Override
			public void run() {
				DB_InfoList.getInstance(mContext).addorUpdateWhiteList(info);
			}
		}).start();
	}

	public void addorUpdateInfoList(List<PeopleInfo> infoList) {
		for (PeopleInfo info : infoList) {
			addorUpdateInfo(info);
		}
	}

	public List<PeopleInfo> getWhiteList() {
		return mList;
	}

	public void delInfo(int position) {
		if (position >= mList.size() && position < 0) {
			return;
		}
		final PeopleInfo info = mList.remove(position);
		if (info != null) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					DB_InfoList.getInstance(mContext).deleteWhiteList(info);
				}
			}).start();
		}
	}
}

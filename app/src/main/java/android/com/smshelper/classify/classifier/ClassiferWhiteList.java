package android.com.smshelper.classify.classifier;

import android.com.smshelper.classify.ClassifyCenter;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.manager.WhiteListManager;
import android.content.Context;

import java.util.List;

/**
 * @author yxf
 * @date 5/3/15
 * @time 3:40 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 白名单过滤器
 */
public class ClassiferWhiteList implements Classifier {
	private Context mContext;

	public ClassiferWhiteList(Context context) {
		mContext = context;
	}

	@Override
	public int classify(String body, String address) {
		List<PeopleInfo> list = WhiteListManager.getInstance(mContext).getWhiteList();
		for (PeopleInfo info : list) {
			if (info.getPhone().equals(address)) {
				return ClassifyCenter.WHITE;
			}
		}
		return ClassifyCenter.UNKNOWN;
	}
}

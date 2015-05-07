package android.com.smshelper.classify.classifier;

import android.com.smshelper.classify.ClassifyCenter;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.manager.BlackListManager;
import android.content.Context;

import java.util.List;

/**
 * @author yxf
 * @date 5/3/15
 * @time 3:38 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 黑名单分类器
 */
public class ClassifierBlackList implements Classifier {
	private Context mContext;

	public ClassifierBlackList(Context context) {
		mContext = context;
	}

	@Override
	public int classify(String body, String address) {
		final List<PeopleInfo> list = BlackListManager.getInstance(mContext).getBlackList();
		for (PeopleInfo info : list) {
			if (info.getPhone().equals(address)) {
				return ClassifyCenter.SPAM;
			}
		}
		return ClassifyCenter.WHITE;
	}
}

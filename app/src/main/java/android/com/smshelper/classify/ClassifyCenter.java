package android.com.smshelper.classify;

import android.com.smshelper.classify.classifier.Classfier;
import android.com.smshelper.classify.classifier.ClassfierBlackList;
import android.com.smshelper.classify.classifier.ClassiferWhiteList;
import android.com.smshelper.classify.classifier.ClassifierKeyword;

/**
 * @author yxf
 * @date 4/30/15
 * @time 9:12 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 过滤器的管理类
 */
public class ClassifyCenter {
	public static final int NORM = 0;
	public static final int SPAM = 1;

	private static ClassifyCenter instance;

	private Classfier mBlackListClassfier;
	private Classfier mWhiteListClassfier;
	private Classfier mKeyWordClassfier;

	public synchronized static ClassifyCenter getInstance() {
		if (instance == null) {
			instance = new ClassifyCenter();
		}
		return instance;
	}

	private ClassifyCenter() {
		mBlackListClassfier = new ClassfierBlackList();
		mWhiteListClassfier = new ClassiferWhiteList();
		mKeyWordClassfier = new ClassifierKeyword();
	}

	public int classify(String body, String address) {
		if (mWhiteListClassfier.classify(body, address) == NORM) {
			//如果在白名单中直接判定为正常短信
			return NORM;
		} else {
			//不在白名单中进行接下来的分类
			if (mBlackListClassfier.classify(body, address) == SPAM) {
				//黑名单中判定为垃圾短信直接判定为垃圾短信
				return SPAM;
			} else if (mKeyWordClassfier.classify(body, address) == SPAM) {
				//关键字判定为垃圾短信，也判定为垃圾短信
				return SPAM;
			}
			//如果没有被判定为垃圾短信则为正常短信
			return NORM;
		}
	}
}

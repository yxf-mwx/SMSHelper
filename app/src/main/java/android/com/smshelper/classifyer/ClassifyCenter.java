package android.com.smshelper.classifyer;

import android.com.smshelper.classifyer.classifier.ClassiferWhiteList;
import android.com.smshelper.classifyer.classifier.Classifier;
import android.com.smshelper.classifyer.classifier.ClassifierBlackList;
import android.com.smshelper.classifyer.classifier.ClassifierKeyword;
import android.com.smshelper.classifyer.classifier.ClassifierSmart;
import android.content.Context;

/**
 * @author yxf
 * @date 4/30/15
 * @time 9:12 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 过滤器的管理类
 */
public class ClassifyCenter {
	public static final int UNKNOWN = 0;
	public static final int WHITE = 1;
	public static final int SPAM = 2;

	private static ClassifyCenter instance;

	private Classifier mBlackListClassifier;
	private Classifier mWhiteListClassifier;
	private Classifier mKeyWordClassifier;
	private Classifier mSmartClassifier;

	private static Context mContext;

	public synchronized static ClassifyCenter getInstance(Context context) {
		if (instance == null) {
			mContext = context.getApplicationContext();
			instance = new ClassifyCenter(mContext);
		}
		return instance;
	}

	private ClassifyCenter(Context context) {
		mBlackListClassifier = new ClassifierBlackList(context);
		mWhiteListClassifier = new ClassiferWhiteList(context);
		mKeyWordClassifier = new ClassifierKeyword();
		mSmartClassifier = new ClassifierSmart(context);
	}

	public int classify(String body, String address) {

		if (mWhiteListClassifier.classify(body, address) == WHITE) {
			//如果在白名单中直接判定为正常短信
			return WHITE;
		}
		//不在白名单中进行接下来的分类

		//黑名单中判定为垃圾短信直接判定为垃圾短信
		if (mBlackListClassifier.classify(body, address) == SPAM) {
			return SPAM;
		}

		//关键字判定为垃圾短信，也判定为垃圾短信
		if (mKeyWordClassifier.classify(body, address) == SPAM) {
			return SPAM;
		}
		//智能分类
		if (mSmartClassifier.classify(body, address) == SPAM) {
			return SPAM;
		}
		//如果没有被判定为垃圾短信则为正常短信
		return WHITE;
	}
}

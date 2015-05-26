package android.com.smshelper.manager;

import android.com.smshelper.database.DB_Data_Smart;
import android.content.Context;

import java.util.Map;

/**
 * @author yxf
 * @date 5/20/15
 * @time 10:44 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 智能过滤管理类
 */
public class SmartKeyListManager {
	private static SmartKeyListManager mInstance;

	private Map<String, Integer> mSpamReflect;
	private Map<String, Integer> mNonSpamReflect;

	public static SmartKeyListManager getInstance() {
		if (mInstance == null) {
			mInstance = new SmartKeyListManager();
		}
		return mInstance;
	}

	public void init(Context context) {
		mSpamReflect = DB_Data_Smart.getInstance(context).getSpamReflect();
		mNonSpamReflect = DB_Data_Smart.getInstance(context).getNonSpamReflect();
	}

	public Map<String, Integer> getSpamReflect() {
		return mSpamReflect;
	}

	public Map<String, Integer> getNonSpamReflect() {
		return mNonSpamReflect;
	}

	//更新过滤算法数据库
	public void updateSmartData(String body, int type) {
		int x = 0;
		x++;
	}
}

package android.com.smshelper;

import android.app.Application;
import android.com.smshelper.manager.KeyWordManager;
import android.content.SharedPreferences;

/**
 * Created by admin on 15-1-21.
 */
public class AppInstance extends Application {
	private static AppInstance mInstance;
	private boolean mIsSmartOn;

	public static AppInstance getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		SharedPreferences sp = getSharedPreferences(AppConstant.SP_FILENAME, MODE_PRIVATE);
		mIsSmartOn = sp.getBoolean(AppConstant.SP_IS_SMART_ON, false);
		KeyWordManager.getInstance().initKeyWordList(this);
	}

	public boolean isSmartOn() {
		return mIsSmartOn;
	}

	public void setIsSmartOn(boolean isSmartOn) {
		mIsSmartOn = isSmartOn;
		SharedPreferences sp = getSharedPreferences(AppConstant.SP_FILENAME, MODE_PRIVATE);
		SharedPreferences.Editor spe = sp.edit();
		spe.putBoolean(AppConstant.SP_IS_SMART_ON, isSmartOn);
		spe.commit();
	}
}


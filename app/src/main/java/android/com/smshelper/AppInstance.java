package android.com.smshelper;

import android.app.Application;
import android.com.smshelper.manager.KeyWordManager;

/**
 * Created by admin on 15-1-21.
 */
public class AppInstance extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		KeyWordManager.getInstance().initKeyWordList(this);
	}
}


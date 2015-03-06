package android.com.smshelper;

import android.app.Application;
import android.com.smshelper.AsyncTask.Async_CallLogs;
import android.com.smshelper.AsyncTask.Async_CopyDmMobile;
import android.content.SharedPreferences;

/**
 * Created by admin on 15-1-21.
 */
public class AppInstance extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		onInit();
	}

	private void onInit() {
		copyDmMobile();
		synchronizeCallLog();
	}

	private void copyDmMobile() {
		SharedPreferences sp = getSharedPreferences(AppConstant.SP_FILENAME, MODE_PRIVATE);
		boolean isFirstInited = sp.getBoolean(AppConstant.SP_INIT_FIRST, false);
		if (isFirstInited) {
			//不是第一次打开
		} else {
			//第一次打开
			Async_CopyDmMobile copyDmMobile = new Async_CopyDmMobile(this);
			copyDmMobile.execute();
		}
	}

	private void synchronizeCallLog() {
		Async_CallLogs runner = new Async_CallLogs(this);
		new Thread(runner).start();
	}
}


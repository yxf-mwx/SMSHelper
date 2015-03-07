package android.com.smshelper;

import android.app.Application;
import android.com.smshelper.AsyncTask.Async_CallLogs;

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
		synchronizeCallLog();
	}



	private void synchronizeCallLog() {
		Async_CallLogs runner = new Async_CallLogs(this);
		new Thread(runner).start();
	}
}


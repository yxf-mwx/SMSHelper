package android.com.smshelper.service;

import android.app.Service;
import android.com.smshelper.reciever.SmsContentObsever;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Telephony;

/**
 * @author yxf
 * @date 15-4-26
 * @time 下午5:41
 * updater xxx
 * update  yy-MM-dd
 * @comment balabalabala
 */
public class BackgroudService extends Service {
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			System.out.println("---handler---");
			switch (msg.what) {

			}
			System.out.println("---handler---");
		}
	};
	SmsContentObsever mObsever;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("service create");
		mObsever = new SmsContentObsever(mHandler);
		getContentResolver().registerContentObserver(Telephony.Sms.Inbox.CONTENT_URI, false, mObsever);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getContentResolver().unregisterContentObserver(mObsever);
	}
}

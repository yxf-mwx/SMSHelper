package android.com.smshelper.reciever;

import android.database.ContentObserver;
import android.os.Handler;

/**
 * @author yxf
 * @date 15-4-26
 * @time 下午5:32
 * updater xxx
 * update  yy-MM-dd
 * @comment balabalabala
 */
public class SmsContentObsever extends ContentObserver {

	/**
	 * Creates a content observer.
	 *
	 * @param handler The handler to run {@link #onChange} on, or null if none.
	 */
	public SmsContentObsever(Handler handler) {
		super(handler);
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		System.out.println("get it");
	}
}

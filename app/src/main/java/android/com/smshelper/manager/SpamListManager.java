package android.com.smshelper.manager;

import android.com.smshelper.db.DB_InfoList;
import android.com.smshelper.entity.SMSEntity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yxf
 * @date 4/30/15
 * @time 9:38 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment balabalabala
 */
public class SpamListManager extends Observable {
	private static SpamListManager mInstance;
	private List<SMSEntity> mList;

	public synchronized static SpamListManager getInstance() {
		if (mInstance == null) {
			mInstance = new SpamListManager();
		}
		return mInstance;
	}

	private SpamListManager() {
		mList = new ArrayList<>();
	}

	public synchronized void addSMS(final Context context, List<SMSEntity> smsList) {
		if (smsList == null || smsList.size() == 0) {
			return;
		}
		ExecutorService execs = Executors.newCachedThreadPool();
		for (final SMSEntity entity : smsList) {
			if (!mList.contains(entity)) {
				mList.add(entity);
				execs.execute(new Runnable() {
					@Override
					public void run() {
						DB_InfoList.getInstance(context).addSpamSMS(entity);
					}
				});
				setChanged();
				notifyObservers();
			}
		}
	}

	public List<SMSEntity> getList() {
		return mList;
	}
}

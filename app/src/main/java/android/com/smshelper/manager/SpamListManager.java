package android.com.smshelper.manager;

import android.com.smshelper.asynctask.Async_Spam_Init;
import android.com.smshelper.database.DB_InfoList;
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
public class SpamListManager extends Observable implements Async_Spam_Init.CallBack {
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

	public synchronized void initSpamList(Context context) {
		Async_Spam_Init initTask = new Async_Spam_Init(context, this);
		initTask.execute();
	}

	public List<SMSEntity> getList() {
		return mList;
	}

	@Override
	public void callBack(List<SMSEntity> list) {
		if (list == null) {
			return;
		}
		mList.clear();
		for (SMSEntity entity : list) {
			mList.add(entity);
		}
		setChanged();
		notifyObservers();
	}

	public void removeSpam(final Context context, final List<SMSEntity> list) {
		for (SMSEntity entity : list) {
			mList.remove(entity);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				DB_InfoList.getInstance(context).removeSpam(list);
			}
		}).start();
	}
}

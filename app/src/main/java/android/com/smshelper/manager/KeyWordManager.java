package android.com.smshelper.manager;

import android.com.smshelper.database.DB_InfoList;
import android.content.Context;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yxf
 * @date 15-4-22
 * @time 上午8:23
 * updater xxx
 * update  yy-MM-dd
 * @comment 关键字管理类
 */
public class KeyWordManager {
	private static KeyWordManager instance;
	private List<String> mList;

	public static synchronized KeyWordManager getInstance() {
		if (instance == null) {
			instance = new KeyWordManager();
		}
		return instance;
	}

	private KeyWordManager() {
		mList = new ArrayList<>();
	}

	public void initKeyWordList(final Context context) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
				DB_InfoList.getInstance(context).initKeyWordList(mList);
			}
		});
		thread.start();
	}

	public synchronized void addKeyWord(final Context context, final String keyword) {
		if (mList.contains(keyword)) {
			return;
		}
		mList.add(keyword);
		new Thread(new Runnable() {
			@Override
			public void run() {
				DB_InfoList.getInstance(context).addKeyword(keyword);
			}
		}).start();
	}

	public List<String> getList() {
		return mList;
	}

	public void updateKeyword(Context context, String original, String newKeyword) {
		deleteKeyword(context, original);
		addKeyWord(context, newKeyword);
	}

	public synchronized void deleteKeyword(final Context context, final String keyword) {
		mList.remove(keyword);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				DB_InfoList.getInstance(context).deleteKeyword(keyword);
			}
		});
		thread.start();
	}
}

package android.com.smshelper.AsyncTask;

import android.com.smshelper.db.DB_InfoList;
import android.com.smshelper.entity.SMSEntity;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * @author yxf
 * @date 5/2/15
 * @time 3:42 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment balabalabala
 */
public class Async_Spam_Init extends AsyncTask<Void, Void, Void> {
	private Context mContext;
	private CallBack mListener;
	private List<SMSEntity> mList;

	public Async_Spam_Init(Context context, CallBack callBack) {
		mContext = context;
		mListener = callBack;
	}

	@Override
	protected Void doInBackground(Void... params) {
		mList = DB_InfoList.getInstance(mContext).initSpamList();
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		if (mListener != null) {
			mListener.callBack(mList);
		}
	}

	public static interface CallBack {
		public void callBack(List<SMSEntity> list);
	}
}

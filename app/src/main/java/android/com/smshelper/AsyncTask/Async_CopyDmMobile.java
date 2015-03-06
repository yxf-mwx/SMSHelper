package android.com.smshelper.AsyncTask;

import android.com.smshelper.AppConstant;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by admin on 15-1-21.
 */
public class Async_CopyDmMobile extends AsyncTask<Object, Object, Boolean> {
	private Context mContext;

	public Async_CopyDmMobile(Context context) {
		mContext = context;
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		final String path = AppConstant.DB_BASEPATH + AppConstant.DB_DM_MOBILE;
		File dir = new File(AppConstant.DB_BASEPATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File f = new File(path);
		InputStream is = null;
		OutputStream os = null;
		try {
			is = mContext.getAssets().open("a.db");
			os = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int count;
			while ((count = is.read(buffer)) > 0) {
				os.write(buffer, 0, count);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	@Override
	protected void onPostExecute(Boolean aBoolean) {
		super.onPostExecute(aBoolean);
		SharedPreferences mSpf = mContext.getSharedPreferences(AppConstant.SP_FILENAME, Context.MODE_PRIVATE);
		if (aBoolean) {
			SharedPreferences.Editor editor = mSpf.edit();
			editor.putBoolean(AppConstant.SP_INIT_FIRST, true);
			editor.commit();
		}
		Log.d(getClass().getSimpleName(), "Copy result: " + (aBoolean ? "success" : "failed"));

	}

}

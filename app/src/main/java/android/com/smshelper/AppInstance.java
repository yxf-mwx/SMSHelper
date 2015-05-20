package android.com.smshelper;

import android.app.Application;
import android.com.smshelper.manager.KeyWordManager;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by admin on 15-1-21.
 */
public class AppInstance extends Application {
	private static AppInstance mInstance;
	private boolean mIsSmartOn;

	public static AppInstance getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		SharedPreferences sp = getSharedPreferences(AppConstant.SP_FILENAME, MODE_PRIVATE);
		mIsSmartOn = sp.getBoolean(AppConstant.SP_IS_SMART_ON, false);
		KeyWordManager.getInstance().initKeyWordList(this);
		final File smartDataFile = new File(AppConstant.DB_BASEPATH + AppConstant.DB_SMART);
		if (!smartDataFile.exists()) {
			copySmartData(smartDataFile);
		}
	}

	public boolean isSmartOn() {
		return mIsSmartOn;
	}

	public void setIsSmartOn(boolean isSmartOn) {
		mIsSmartOn = isSmartOn;
		SharedPreferences sp = getSharedPreferences(AppConstant.SP_FILENAME, MODE_PRIVATE);
		SharedPreferences.Editor spe = sp.edit();
		spe.putBoolean(AppConstant.SP_IS_SMART_ON, isSmartOn);
		spe.commit();
	}

	private void copySmartData(File dataFile) {
		File Base = new File(AppConstant.DB_BASEPATH);
		if (!Base.exists()) {
			Base.mkdirs();
		}
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		InputStream is = null;
		OutputStream os = null;
		try {
			is = getAssets().open("data.db");
			os = new FileOutputStream(dataFile);
			byte[] buffer = new byte[1024];
			int count;
			while ((count = is.read(buffer)) > 0) {
				os.write(buffer, 0, count);
			}
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
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
	}
}


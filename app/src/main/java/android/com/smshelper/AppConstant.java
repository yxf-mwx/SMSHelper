package android.com.smshelper;

import android.os.Environment;

/**
 * Created by admin on 15-1-14.
 */
public class AppConstant {
	public final static int TAG_POSTION = 0xffffffff;
	public static final String FRAGMENT_TAG_MANUALINPUT = "MANUAL_INPUT";
	/**
	 * SharePrefences
	 */
	public final static String DB_BASEPATH = Environment.getExternalStorageDirectory() + "/com.android.smshelper/db";
	public final static String DB_DM_MOBILE = "/a.db";

	public static String DB_CALLLOGS_CACHE="b.db";

	/**
	 * SharePrefences
	 */
	public final static String SP_FILENAME = "app.xml";
	public final static String SP_INIT_FIRST = "a";
}

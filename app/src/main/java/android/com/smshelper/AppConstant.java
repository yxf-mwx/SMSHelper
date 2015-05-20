package android.com.smshelper;

import android.os.Environment;

/**
 * Created by admin on 15-1-14.
 */
public class AppConstant {
	public final static int TAG_POSTION = 0xffffffff;
	public static final String FRAGMENT_TAG_MANUALINPUT = "MANUAL_INPUT";

	/**
	 * DB
	 */
	public final static String DB_BASEPATH = Environment.getExternalStorageDirectory() + "/com.android.smshelper/db";
	public final static String DB_DM_MOBILE = "/a.db";
	public final static String DB_SMART = "/data.db";

	public static String DB_CALLLOGS_CACHE = "b.db";
	/**
	 * SharePrefences
	 */
	public final static String SP_FILENAME = "app.xml";
	public final static String SP_INIT_FIRST = "a";
	public final static String SP_IS_SMART_ON = "b";

	/**
	 * ARGS
	 */
	//RESULT
	public final static int RESULTCODE_CALLLOGS = 0;
	public final static int RESULTCODE_MANUALINPUT = 1;
	public final static int RESULTCODE_CONTACTS = 2;
	public final static int RESULTCODE_SMSLOG = 3;

	//	REQUEST
	public final static int REQUESTCODE_BLACKLIST = 0;
	public final static int REQUESTCODE_WHITELIST = 1;
	public final static int REQUESTCODE_MAIN = 2;

	public final static String ARGS_SELECTLIST = "resultlist";

	public final static String ARGS_SMSENTITY = "sms_entity";
}

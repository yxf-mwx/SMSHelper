package android.com.smshelper.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 15-1-20.
 */
public class DateUtils {
	public static String getDate(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
		String result = df.format(date);
		return result;
	}
}

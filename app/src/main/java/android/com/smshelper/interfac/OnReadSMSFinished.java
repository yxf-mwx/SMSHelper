package android.com.smshelper.interfac;

import android.com.smshelper.entity.ListItemSMS;

import java.util.List;

/**
 * Created by yxf on 15-4-8.
 */
public interface OnReadSMSFinished {
	public void onReadSMSFinished(List<ListItemSMS> list);
}

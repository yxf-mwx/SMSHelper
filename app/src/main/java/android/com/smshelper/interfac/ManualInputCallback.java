package android.com.smshelper.interfac;

import android.com.smshelper.entity.PeopleInfo;

import java.util.List;

/**
 * Created by admin on 15-1-17.
 */
public interface ManualInputCallback {
	public void callback(List<PeopleInfo> infoList);
}

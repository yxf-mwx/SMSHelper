package android.com.smshelper.activity.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

/**
 * @author yxf
 * @date 15-4-20
 * @time ??9:25
 * updater xxx
 * update  yy-MM-dd
 * @comment balabalabala
 */
public abstract class BaseActivitySMS extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final int id = item.getItemId();
		switch (id) {
			case android.R.id.home:
				this.finish();
				return true;
			default:
				return true;
		}
	}

	protected abstract void initActionBar();
}

package android.com.smshelper.activity;

import android.com.smshelper.R;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

/**
 * Created by admin on 15-1-20.
 */
public class ContactsActivity extends ActionBarActivity {
	private ListView mLvMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commonlist);
		mLvMain=(ListView)findViewById(R.id.lv_main_common);
	}


}

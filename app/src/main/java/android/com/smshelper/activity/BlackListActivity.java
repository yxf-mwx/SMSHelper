package android.com.smshelper.activity;

import android.com.smshelper.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by admin on 15-1-9.
 */
public class BlackListActivity extends ActionBarActivity implements View.OnClickListener {
	private ListView mLvMain;
	private TextView mTvAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balcklist);
		mLvMain = (ListView) findViewById(R.id.lv_blacklist);
		mTvAdd = (TextView) findViewById(R.id.tv_add_blacklist);
		mTvAdd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		final int id = v.getId();
		switch (id) {
			case R.id.tv_add_blacklist:
				Intent intent = new Intent(BlackListActivity.this, ManualInputActivity.class);
				startActivity(intent);
				break;

		}
	}
}

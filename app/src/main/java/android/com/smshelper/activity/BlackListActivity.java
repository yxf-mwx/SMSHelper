package android.com.smshelper.activity;

import android.com.smshelper.R;
import android.com.smshelper.adapter.AdapterBlackList;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.manager.BlackListManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

/**
 * Created by admin on 15-1-9.
 */
public class BlackListActivity extends ActionBarActivity implements View.OnClickListener {
	private ListView mLvMain;
	private List<PeopleInfo> mList;
	private AdapterBlackList mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balcklist);
		initActionBar();
		mLvMain = (ListView) findViewById(R.id.lv_blacklist);
		mList = BlackListManager.getInstance().getBlackList();
		mAdapter = new AdapterBlackList(this, mList);
		mLvMain.setAdapter(mAdapter);
	}


	@Override
	public void onClick(View v) {
		final int id = v.getId();
		switch (id) {
		}
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

	private void initActionBar() {
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(getString(R.string.blacklist_management));
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}

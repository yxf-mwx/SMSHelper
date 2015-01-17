package android.com.smshelper.activity;

import android.com.smshelper.AppConstant;
import android.com.smshelper.R;
import android.com.smshelper.adapter.AdapterBlackList;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.fragment.DialogFragment_ManualInput;
import android.com.smshelper.interfac.ManualInputCallback;
import android.com.smshelper.manager.BlackListManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin on 15-1-9.
 */
public class BlackListActivity extends ActionBarActivity implements View.OnClickListener, ManualInputCallback {
	private ListView mLvMain;
	private List<PeopleInfo> mList;
	private AdapterBlackList mAdapter;
	private TextView mTvBottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balcklist);
		initActionBar();
		mLvMain = (ListView) findViewById(R.id.lv_blacklist);
		mTvBottom = (TextView) findViewById(R.id.tv_add_blacklist);
		mList = BlackListManager.getInstance().getBlackList();
		mAdapter = new AdapterBlackList(this, mList);
		mLvMain.setAdapter(mAdapter);
		mTvBottom.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		final int id = v.getId();
		switch (id) {
			case R.id.tv_add_blacklist:
				showAddDialog();
				return;
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

	private void showAddDialog() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		DialogFragment_ManualInput dfmt = DialogFragment_ManualInput.newInstance(this);
		ft.add(dfmt, AppConstant.FRAGMENT_TAG_MANUALINPUT);
		ft.commit();
	}

	@Override
	public void callback(PeopleInfo info) {
//		Toast.makeText(this, info.toString(), Toast.LENGTH_LONG).show();
		BlackListManager.getInstance().addorUpdateInfo(info);
		mAdapter.notifyDataSetChanged();
	}
}

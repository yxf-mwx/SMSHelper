package android.com.smshelper.activity;

import android.com.smshelper.AppConstant;
import android.com.smshelper.R;
import android.com.smshelper.adapter.AdapterWhiteList;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.fragment.DialogFragment_AddList;
import android.com.smshelper.interfac.ManualInputCallback;
import android.com.smshelper.manager.WhiteListManager;
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
 * Created by admin on 15-1-18.
 */
public class WhiteListActivity extends ActionBarActivity implements View.OnClickListener, ManualInputCallback {
	private static final int TYPE = 2;
	private ListView mLvMain;
	private TextView mTvAdd;
	private List<PeopleInfo> mList;
	private AdapterWhiteList mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		mLvMain = (ListView) findViewById(R.id.lv_main_infolist);
		mTvAdd = (TextView) findViewById(R.id.tv_add_common);
		mTvAdd.setOnClickListener(this);
		mList = WhiteListManager.getInstance(this).getWhiteList();
		mAdapter = new AdapterWhiteList(this, mList);
		mLvMain.setAdapter(mAdapter);
		initActionBar();
	}

	@Override
	public void onClick(View v) {
		final int id = v.getId();
		switch (id) {
			case R.id.tv_add_common:
				showAddDialog();
				break;
		}
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("白名单管理");
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private void showAddDialog() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		DialogFragment_AddList dfal = DialogFragment_AddList.newInstance(this, TYPE);
		ft.add(dfal, AppConstant.FRAGMENT_TAG_MANUALINPUT);
		ft.commit();
	}

	@Override
	public void callback(PeopleInfo info) {
		WhiteListManager.getInstance(this).addorUpdateInfo(info);
		mAdapter.notifyDataSetChanged();
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
}

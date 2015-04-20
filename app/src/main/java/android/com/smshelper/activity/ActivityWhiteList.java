package android.com.smshelper.activity;

import android.com.smshelper.AppConstant;
import android.com.smshelper.R;
import android.com.smshelper.activity.activitybase.BaseActivitySMS;
import android.com.smshelper.adapter.AdapterWhiteList;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.fragment.DialogFragment_AddList;
import android.com.smshelper.interfac.AddInfoListCallback;
import android.com.smshelper.manager.WhiteListManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15-1-18.
 */
public class ActivityWhiteList extends BaseActivitySMS implements View.OnClickListener, AddInfoListCallback {
	private static final int TYPE = 2;
	private static final int REQUESTCODE = 0;
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

	@Override
	protected void initActionBar() {
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
	public void callback(int type) {
		Intent intent;
		switch (type) {
			case 0:
			case 1:
				intent = new Intent(this, ActivityCallLogs.class);
				startActivityForResult(intent, REQUESTCODE);
				break;
			case 2:
			case 3:
			default:
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ArrayList<PeopleInfo> infoList = data.getParcelableArrayListExtra("resultlist");
		WhiteListManager.getInstance(this).addorUpdateInfoList(infoList);
		mAdapter.notifyDataSetChanged();
	}
}
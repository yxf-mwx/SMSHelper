package android.com.smshelper.activity;

import android.com.smshelper.AppConstant;
import android.com.smshelper.R;
import android.com.smshelper.adapter.AdapterInfoList;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.fragment.DialogFragment_AddList;
import android.com.smshelper.interfac.AddInfoListCallback;
import android.com.smshelper.manager.BlackListManager;
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
 * Created by admin on 15-1-9.
 */
public class ActivityBlackList extends ActivityBase implements View.OnClickListener, AddInfoListCallback {
	private static final int TYPE = 1;
	private static final int REQUESTCODE = 0;
	private ListView mLvMain;
	private List<PeopleInfo> mList;
	private AdapterInfoList mAdapter;
	private TextView mTvBottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		mLvMain = (ListView) findViewById(R.id.lv_main_infolist);
		mTvBottom = (TextView) findViewById(R.id.tv_add_common);
		mList = BlackListManager.getInstance(this).getBlackList();
		mAdapter = new AdapterInfoList(this, mList);
		mLvMain.setAdapter(mAdapter);
		mTvBottom.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		final int id = v.getId();
		switch (id) {
			case R.id.tv_add_common:
				showAddDialog();
				return;
		}
	}

	@Override
	protected void initActionBar() {
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(getString(R.string.blacklist_management));
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
				intent = new Intent(this, ActivityManualInput.class);
				startActivityForResult(intent, REQUESTCODE);
				break;
			case 1:
				intent = new Intent(this, ActivityCallLogs.class);
				startActivityForResult(intent, REQUESTCODE);
				break;
			case 2:
				intent = new Intent(this, ActivitySMS.class);
				startActivityForResult(intent, REQUESTCODE);
				break;
			case 3:
				intent = new Intent(this, ActivityContacts.class);
				startActivityForResult(intent, REQUESTCODE);
				break;
			default:
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		switch (requestCode) {
			case REQUESTCODE:
//				switch (resultCode) {
//					case AppConstant.RESULTCODE_CALLLOGS:
//					case AppConstant.RESULTCODE_CONTACTS:
//					case AppConstant.RESULTCODE_MANUALINPUT:
//					case AppConstant.RESULTCODE_SMSLOG:
//				}
				ArrayList<PeopleInfo> infoList = data.getParcelableArrayListExtra(AppConstant.ARGS_SELECTLIST);
				BlackListManager.getInstance(this).addorUpdateInfoList(infoList);
				mAdapter.notifyDataSetChanged();
				break;
		}
	}
}

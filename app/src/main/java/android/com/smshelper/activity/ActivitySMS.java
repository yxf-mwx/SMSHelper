package android.com.smshelper.activity;

import android.com.smshelper.AppConstant;
import android.com.smshelper.asynctask.Async_SMS;
import android.com.smshelper.R;
import android.com.smshelper.activity.activitybase.BaseActivitySMS;
import android.com.smshelper.adapter.AdapterSMS;
import android.com.smshelper.entity.ListItemSMS;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.interfac.OnReadSMSFinished;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxf on 15-4-7.
 */
public class ActivitySMS extends BaseActivitySMS implements OnReadSMSFinished, View.OnClickListener {
	private ListView mListView;
	private List<ListItemSMS> mListData;
	private AdapterSMS mAdapter;
	private TextView mBtnAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		mListView = (ListView) findViewById(R.id.lv_main_infolist);
		mListData = new ArrayList<>();
		mAdapter = new AdapterSMS(this, mListData);
		mListView.setAdapter(mAdapter);
		mBtnAdd = (TextView) findViewById(R.id.tv_add_common);
		mBtnAdd.setOnClickListener(this);
		getSMS();
	}

	private void getSMS() {
		Async_SMS async_sms = new Async_SMS(this, this);
		async_sms.execute();
	}

	@Override
	public void onReadSMSFinished(List<ListItemSMS> list) {
		if (list != null) {
			mListData.clear();
			mListData.addAll(list);
			list.clear();
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void initActionBar() {
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(getString(R.string.activity_sms));
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		ArrayList<PeopleInfo> list = new ArrayList<>();
		for (ListItemSMS item : mListData) {
			if (item.isCheck()) {
				final String name = item.getPersion() == null ? "" : item.getPersion();
				final String phone = item.getAddress();
				list.add(new PeopleInfo(name, phone));
			}
		}
		intent.putParcelableArrayListExtra(AppConstant.ARGS_SELECTLIST, list);
		this.setResult(AppConstant.RESULTCODE_SMSLOG, intent);
		this.finish();
	}
}

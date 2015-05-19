package android.com.smshelper.activity;

import android.com.smshelper.AppConstant;
import android.com.smshelper.asynctask.Async_CallLogs;
import android.com.smshelper.R;
import android.com.smshelper.activity.base.BaseActivitySMS;
import android.com.smshelper.adapter.AdapterCallLogs;
import android.com.smshelper.database.DB_CallLogs_Cache;
import android.com.smshelper.entity.CallLogs;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.interfac.AsyncCallBack;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15-1-20.
 */
public class ActivityCallLogs extends BaseActivitySMS implements View.OnClickListener, AsyncCallBack {

	private List<CallLogs> mList;
	private View mLayout;
	private ListView mLvMain;
	private TextView mTvConfirm;

	private AdapterCallLogs mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commonlist);
		mLvMain = (ListView) findViewById(R.id.lv_main_common);
		mTvConfirm = (TextView) findViewById(R.id.tv_add_common);
		mLayout = findViewById(R.id.layout_loading_common);
		mList = new ArrayList<>();
		mAdapter = new AdapterCallLogs(this, mList);
		mLvMain.setAdapter(mAdapter);
		mTvConfirm.setOnClickListener(this);
		initActionBar();
		//同步通话记录缓存数据库
		synchronizeCallLog();
	}

	private List<CallLogs> getCallLogsList() {
		return DB_CallLogs_Cache.getInstance(this).getList();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_add_common:
				ArrayList<PeopleInfo> list = new ArrayList<>();
				for (CallLogs log : mList) {
					final boolean isCheck = log.isCheck();
					if (isCheck) {
						final String name = log.getName();
						final String phone = log.getPhone();
						PeopleInfo info = new PeopleInfo(name, phone);
						list.add(info);
					}
				}
				Intent intent = new Intent();
				intent.putParcelableArrayListExtra(AppConstant.ARGS_SELECTLIST, list);
				setResult(AppConstant.RESULTCODE_CALLLOGS, intent);
				this.finish();
				break;
		}
	}

	private void synchronizeCallLog() {
		Async_CallLogs runner = new Async_CallLogs(this, this);
		new Thread(runner).start();
	}

	@Override
	public void onFinished() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mLayout.setVisibility(View.GONE);
				List<CallLogs> data = getCallLogsList();
				mList.clear();
				for (CallLogs log : data) {
					mList.add(log);
				}
			}
		});
	}

	protected void initActionBar() {
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(getString(R.string.activity_callogs));
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}

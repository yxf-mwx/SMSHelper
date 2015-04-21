package android.com.smshelper.activity;

import android.com.smshelper.R;
import android.com.smshelper.activity.activitybase.BaseActivitySMS;
import android.com.smshelper.adapter.AdapterKeyWord;
import android.com.smshelper.interfac.OnItemClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yxf
 * @date 15-4-21
 * @time ??9:29
 * updater xxx
 * update  yy-MM-dd
 * @comment balabalabala
 */
public class ActivityKeyWords extends BaseActivitySMS implements View.OnClickListener, OnItemClickListener {
	private ListView mListView;
	private TextView mTvAdd;
	private List<String> mListData;
	private AdapterKeyWord mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commonlist);
		mListView = (ListView) findViewById(R.id.lv_main_common);
		//todo ???????
		mListData = new ArrayList<>();
		mAdapter = new AdapterKeyWord(this, mListData, this);
		mListView.setAdapter(mAdapter);
		mTvAdd = (TextView) findViewById(R.id.tv_add_common);
		mTvAdd.setOnClickListener(this);
	}

	@Override
	protected void initActionBar() {
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(getString(R.string.activity_keywords));
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void OnItemClick(View view, int position) {

	}
}

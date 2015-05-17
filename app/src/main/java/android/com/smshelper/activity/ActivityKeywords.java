package android.com.smshelper.activity;

import android.com.smshelper.R;
import android.com.smshelper.activity.base.BaseActivitySMS;
import android.com.smshelper.adapter.AdapterKeyWord;
import android.com.smshelper.fragment.DialogFragment_Keyword;
import android.com.smshelper.interfac.OnItemClickListener;
import android.com.smshelper.manager.KeyWordManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * @author yxf
 * @date 15-4-21
 * @time 下午9:29
 * updater xxx
 * update  yy-MM-dd
 * @comment 关键字界面类
 */
public class ActivityKeywords extends BaseActivitySMS implements View.OnClickListener, OnItemClickListener,
		DialogFragment_Keyword.CallBack {
	private ListView mListView;
	private TextView mTvAdd;
	private List<String> mListData;
	private AdapterKeyWord mAdapter;
	private View mViewLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commonlist);
		mListView = (ListView) findViewById(R.id.lv_main_common);
		mListData = KeyWordManager.getInstance().getList();
		mAdapter = new AdapterKeyWord(this, mListData, this);
		mListView.setAdapter(mAdapter);
		mTvAdd = (TextView) findViewById(R.id.tv_add_common);
		mTvAdd.setOnClickListener(this);
		mViewLoading = findViewById(R.id.layout_loading_common);
		mViewLoading.setVisibility(View.GONE);
	}

	@Override
	protected void initActionBar() {
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(getString(R.string.activity_keywords));
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_add_common:
				DialogFragment_Keyword.newInstance(this).show(getSupportFragmentManager(), "keyword");
				break;
		}
	}

	@Override
	public void OnItemClick(View view, int position) {
		switch (view.getId()) {
			case R.id.layout_listitem_keyword:
				DialogFragment_Keyword.newInstance(this, mListData.get(position)).show(
						getSupportFragmentManager(),
						"keyword");
				break;
			case R.id.tv_keyword_delete:
				KeyWordManager.getInstance().deleteKeyword(this, mListData.get(position));
				mAdapter.notifyDataSetChanged();
				break;
			default:
		}
	}

	@Override
	public void callback(String keyword, String original) {
		if (TextUtils.isEmpty(original)) {
			KeyWordManager.getInstance().addKeyWord(this, keyword);
			mAdapter.notifyDataSetChanged();
		} else {
			KeyWordManager.getInstance().updateKeyword(this, original, keyword);
			mAdapter.notifyDataSetChanged();
		}
	}
}

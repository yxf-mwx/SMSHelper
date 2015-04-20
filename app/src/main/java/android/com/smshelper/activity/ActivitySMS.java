package android.com.smshelper.activity;

import android.com.smshelper.AsyncTask.Async_SMS;
import android.com.smshelper.R;
import android.com.smshelper.adapter.AdapterSMS;
import android.com.smshelper.entity.ListItemSMS;
import android.com.smshelper.interfac.OnReadSMSFinished;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxf on 15-4-7.
 */
public class ActivitySMS extends ActivityBase implements OnReadSMSFinished {
	private ListView mListView;
	private List<ListItemSMS> mListData;
	private AdapterSMS mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		mListView = (ListView) findViewById(R.id.lv_main_infolist);
		mListData = new ArrayList<>();
		mAdapter = new AdapterSMS(this, mListData);
		mListView.setAdapter(mAdapter);

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
}

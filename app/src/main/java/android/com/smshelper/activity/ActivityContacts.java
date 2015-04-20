package android.com.smshelper.activity;

import android.com.smshelper.AppConstant;
import android.com.smshelper.AsyncTask.Async_Contacts;
import android.com.smshelper.R;
import android.com.smshelper.adapter.AdapterContacts;
import android.com.smshelper.entity.Contact;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.interfac.OnReadContactFinished;
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
public class ActivityContacts extends ActivityBase implements OnReadContactFinished, View.OnClickListener {
	private List<Contact> mList;

	private ListView mLvMain;
	private View mLayoutLoading;
	private TextView mBtnAdd;
	private AdapterContacts mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commonlist);
		initActionBar();
		mLvMain = (ListView) findViewById(R.id.lv_main_common);
		mLayoutLoading = findViewById(R.id.layout_loading_common);
		mBtnAdd = (TextView) findViewById(R.id.tv_add_common);
		mBtnAdd.setOnClickListener(this);
		mList = new ArrayList<>();
		mAdapter = new AdapterContacts(this, mList);
		mLvMain.setAdapter(mAdapter);
		Async_Contacts thread = new Async_Contacts(this, this);
		thread.execute();
	}

	protected void initActionBar() {
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(getString(R.string.activity_contacts));
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onFinish(final List<Contact> list) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mLayoutLoading.setVisibility(View.GONE);
				mList.clear();
				for (Contact contact : list) {
					mList.add(contact);
				}
				mAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void onClick(View v) {
		ArrayList<PeopleInfo> result = new ArrayList<>();
		for (Contact contact : mList) {
			if (contact.isCheck()) {
				final String nick = contact.getNick();
				final String phone = contact.getPhone();
				result.add(new PeopleInfo(nick, phone));
			}
		}
		Intent intent = new Intent();
		intent.putParcelableArrayListExtra(AppConstant.ARGS_SELECTLIST, result);
		setResult(AppConstant.RESULTCODE_CONTACTS, intent);
		this.finish();
	}
}

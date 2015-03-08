package android.com.smshelper.activity;

import android.com.smshelper.AsyncTask.Async_Contacts;
import android.com.smshelper.R;
import android.com.smshelper.adapter.AdapterContacts;
import android.com.smshelper.entity.Contact;
import android.com.smshelper.interfac.OnReadContactFinished;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15-1-20.
 */
public class ActivityContacts extends ActionBarActivity implements OnReadContactFinished {
	private List<Contact> mList;

	private ListView mLvMain;
	private View mLayoutLoading;
	private AdapterContacts mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commonlist);
		initActionBar();
		mLvMain = (ListView) findViewById(R.id.lv_main_common);
		mLayoutLoading = findViewById(R.id.layout_loading_common);
		mList = new ArrayList<>();
		mAdapter = new AdapterContacts(this, mList);
		mLvMain.setAdapter(mAdapter);
		Async_Contacts thread = new Async_Contacts(this, this);
		thread.execute();
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
}

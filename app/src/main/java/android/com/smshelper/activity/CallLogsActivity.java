package android.com.smshelper.activity;

import android.com.smshelper.R;
import android.com.smshelper.adapter.Adapter_CallLogs;
import android.com.smshelper.db.DB_CallLogs_Cache;
import android.com.smshelper.entity.CallLogs;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin on 15-1-20.
 */
public class CallLogsActivity extends ActionBarActivity implements View.OnClickListener {
	private List<CallLogs> mList;
	private ListView mLvMain;
	private TextView mTvConfirm;
	private Adapter_CallLogs mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commonlist);
		mLvMain = (ListView) findViewById(R.id.lv_main_common);
		mTvConfirm = (TextView) findViewById(R.id.tv_add_common);
		mList = getCallLogsList();
		mAdapter = new Adapter_CallLogs(this, mList);
		mLvMain.setAdapter(mAdapter);
		mTvConfirm.setOnClickListener(this);
	}

	private List<CallLogs> getCallLogsList() {

		return DB_CallLogs_Cache.getInstance(this).getList();
	}

	@Override
	public void onClick(View v) {

	}
}

package android.com.smshelper.activity;

import android.com.smshelper.R;
import android.com.smshelper.adapter.Adapter_CallLogs;
import android.com.smshelper.entity.CallLogs;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<String, CallLogs> map = new HashMap<>();
		List<CallLogs> result = new ArrayList<>();
		ContentResolver resolver = getContentResolver();
		Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI,
				new String[]{
						CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,
						CallLog.Calls.TYPE, CallLog.Calls.DATE
				},
				null,
				null,
				CallLog.Calls.DEFAULT_SORT_ORDER);
		while (cursor.moveToNext()) {
			final String phone = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
			final String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
			final Date date = new Date(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
			final int type = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));
			String typeStr = "";
			switch (type) {
				case CallLog.Calls.INCOMING_TYPE:
					typeStr = "来电";
					break;
				case CallLog.Calls.OUTGOING_TYPE:
					typeStr = "去电";
					break;
				case CallLog.Calls.MISSED_TYPE:
					typeStr = "未接";
			}
			if (map.get(phone) == null) {
				CallLogs cl = new CallLogs(phone, name, date, "未知", typeStr);
				map.put(phone, cl);
				result.add(cl);
			}
		}
		return result;
	}

	@Override
	public void onClick(View v) {

	}
}

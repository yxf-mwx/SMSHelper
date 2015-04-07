package android.com.smshelper.activity;

import android.com.smshelper.R;
import android.com.smshelper.adapter.AdapterSMS;
import android.com.smshelper.entity.ListItemSMS;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.List;

/**
 * Created by yxf on 15-4-7.
 */
public class ActivitySMS extends ActionBarActivity {
	private ListView mListView;
	private List<ListItemSMS> mListData;
	private AdapterSMS mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		mListView = (ListView) findViewById(R.id.lv_main_infolist);

	}
}

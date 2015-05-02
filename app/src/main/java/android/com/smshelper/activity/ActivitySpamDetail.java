package android.com.smshelper.activity;

import android.com.smshelper.AppConstant;
import android.com.smshelper.R;
import android.com.smshelper.Util.DateUtils;
import android.com.smshelper.activity.activitybase.BaseActivitySMS;
import android.com.smshelper.entity.SMSEntity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import java.util.Date;

/**
 * @author yxf
 * @date 5/2/15
 * @time 8:00 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment balabalabala
 */
public class ActivitySpamDetail extends BaseActivitySMS {
	private TextView mTvAddress;
	private TextView mTvDate;
	private TextView mTvContent;
	private SMSEntity mSMSEntity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spamdetail);
		if (savedInstanceState == null) {
			mSMSEntity = getIntent().getParcelableExtra(AppConstant.ARGS_SMSENTITY);
		}
		mTvAddress = (TextView) findViewById(R.id.tv_spamdetail_address);
		mTvDate = (TextView) findViewById(R.id.tv_spamdetail_date);
		mTvContent = (TextView) findViewById(R.id.tv_spamdetail_content);
		final long date = mSMSEntity.getDate();
		final String dateStr = DateUtils.getDate(new Date(date));
		mTvAddress.setText(getString(R.string.number, mSMSEntity.getAddress()));
		mTvDate.setText(getString(R.string.date, dateStr));
		mTvContent.setText(mSMSEntity.getBody());
	}

	@Override
	protected void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(R.string.activity_spamdetail);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}

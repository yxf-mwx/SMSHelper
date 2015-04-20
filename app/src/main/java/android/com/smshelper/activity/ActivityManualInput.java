package android.com.smshelper.activity;

import android.com.smshelper.AppConstant;
import android.com.smshelper.R;
import android.com.smshelper.entity.PeopleInfo;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by admin on 15-1-9.
 */
public class ActivityManualInput extends ActivityBase implements View.OnClickListener {
	private EditText mEdtPhone;
	private EditText mEdtNick;
	private Button mBtnAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manualinput);
		initActionBar();
		mEdtPhone = (EditText) findViewById(R.id.edt_manualinput_phone);
		mEdtNick = (EditText) findViewById(R.id.edt_manualinput_nick);
		mBtnAdd = (Button) findViewById(R.id.btn_manualinput_save);
		mBtnAdd.setOnClickListener(this);
	}

	@Override

	protected void initActionBar() {
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(getString(R.string.activity_manualinput));
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		ArrayList<PeopleInfo> list = new ArrayList<>();
		final String phone = mEdtPhone.getText().toString();
		final String nick = mEdtNick.getText().toString();
		if (TextUtils.isEmpty(phone)) {
			Toast.makeText(this, "号码不能为空！", Toast.LENGTH_SHORT).show();
			return;
		}
		list.add(new PeopleInfo(nick, phone));
		intent.putParcelableArrayListExtra(AppConstant.ARGS_SELECTLIST, list);
		this.setResult(AppConstant.RESULTCODE_MANUALINPUT, intent);
		this.finish();
	}
}

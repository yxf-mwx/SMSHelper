package android.com.smshelper.fragment;

import android.com.smshelper.AppInstance;
import android.com.smshelper.R;
import android.com.smshelper.activity.ActivityBlackList;
import android.com.smshelper.activity.ActivityKeywords;
import android.com.smshelper.activity.ActivityWhiteList;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by admin on 15-1-11.
 */
public class Fragment_LeftMenu extends Fragment implements View.OnClickListener, CompoundButton
		.OnCheckedChangeListener {
	TextView mTVBlackList;
	TextView mTVWhiteList;
	TextView mTVKeyWord;
	CheckBox mCbSmart;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return View.inflate(getActivity(), R.layout.fragment_leftmenu_fragment, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mTVBlackList = (TextView) view.findViewById(R.id.tv_blacklist_leftmenu);
		mTVBlackList.setOnClickListener(this);
		mTVWhiteList = (TextView) view.findViewById(R.id.tv_whitelist_leftmenu);
		mTVWhiteList.setOnClickListener(this);
		mTVKeyWord = (TextView) view.findViewById(R.id.tv_keyword_leftmenu);
		mTVKeyWord.setOnClickListener(this);
		mCbSmart = (CheckBox) view.findViewById(R.id.cb_smartfilter_leftmenu);
		mCbSmart.setChecked(AppInstance.getInstance().isSmartOn());
		mCbSmart.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		final int id = v.getId();
		switch (id) {
			case R.id.tv_blacklist_leftmenu:
				intent = new Intent(getActivity(), ActivityBlackList.class);
				getActivity().startActivity(intent);
				return;
			case R.id.tv_whitelist_leftmenu:
				intent = new Intent(getActivity(), ActivityWhiteList.class);
				getActivity().startActivity(intent);
				return;
			case R.id.tv_keyword_leftmenu:
				intent = new Intent(getActivity(), ActivityKeywords.class);
				getActivity().startActivity(intent);
			default:
				return;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		AppInstance.getInstance().setIsSmartOn(isChecked);
	}
}

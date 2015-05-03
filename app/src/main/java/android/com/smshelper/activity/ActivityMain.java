package android.com.smshelper.activity;

import android.com.smshelper.AppConstant;
import android.com.smshelper.AsyncTask.Async_Spam_Import;
import android.com.smshelper.AsyncTask.Async_Spam_Recover;
import android.com.smshelper.R;
import android.com.smshelper.adapter.AdapterSpam;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.entity.SMSEntity;
import android.com.smshelper.interfac.OnItemClickListener;
import android.com.smshelper.interfac.OnItemLongClickListener;
import android.com.smshelper.manager.SpamListManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class ActivityMain extends ActionBarActivity implements MenuDrawer.OnDrawerStateChangeListener, View
		.OnClickListener, Observer, OnItemClickListener, OnItemLongClickListener, Async_Spam_Import.CallBack {
	private MenuDrawer mMenuDrawer;
	private ListView mListView;
	private TextView mTvbtn;
	private View mVLoading;
	private AdapterSpam mAdapter;
	private List<SMSEntity> mListData;
	private boolean mIsActionMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commonlist);
		mListView = (ListView) findViewById(R.id.lv_main_common);
		mTvbtn = (TextView) findViewById(R.id.tv_add_common);
		mTvbtn.setText("从短信中导入");
		mTvbtn.setOnClickListener(this);
		mVLoading = findViewById(R.id.layout_loading_common);
		mListData = SpamListManager.getInstance().getList();
		mAdapter = new AdapterSpam(this, mListData, this, this);
		mListView.setAdapter(mAdapter);
		initActionBar();
		initMenuDrawer();
		SpamListManager.getInstance().initSpamList(this);
		SpamListManager.getInstance().addObserver(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case android.R.id.home:
				mMenuDrawer.openMenu();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void initMenuDrawer() {
		final int menuSize = getResources().getDisplayMetrics().widthPixels * 2 / 3;
		mMenuDrawer = MenuDrawer.attach(
				ActivityMain.this,
				MenuDrawer.Type.BEHIND,
				Position.LEFT,
				MenuDrawer.MENU_DRAG_WINDOW
		);
		mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
		mMenuDrawer.setMenuView(R.layout.view_leftmenu);
		mMenuDrawer.setMenuSize(menuSize);
		mMenuDrawer.setOnDrawerStateChangeListener(this);
	}

	@Override
	public void onDrawerStateChange(int oldState, int newState) {
		int logoRes = 0;
		if (newState == MenuDrawer.STATE_CLOSED) {
			logoRes = R.drawable.ic_actionbar_menu;
		} else {
			logoRes = R.drawable.ic_actionbar_back;
		}
		if (logoRes != 0) {
			getSupportActionBar().setHomeAsUpIndicator(logoRes);
		}
	}

	@Override
	public void onDrawerSlide(float openRatio, int offsetPixels) {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SpamListManager.getInstance().deleteObserver(this);
		mMenuDrawer.setOnDrawerStateChangeListener(null);
		mMenuDrawer = null;
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.layout_actionbar);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeAsUpIndicator(R.drawable.ic_actionbar_menu);
	}

	@Override
	public void onClick(View v) {
		if (mIsActionMode) {
			switch (v.getId()) {
				case R.id.tv_add_common:
					if (isAllSelect()) {
						for (SMSEntity entity : mListData) {
							entity.setIsCheck(false);
						}
					} else {
						for (SMSEntity entity : mListData) {
							entity.setIsCheck(true);
						}
					}
					break;
			}
			mAdapter.notifyDataSetChanged();
			updateBtnText();
		} else {
			switch (v.getId()) {
				case R.id.tv_add_common:
					Intent intent = new Intent(ActivityMain.this, ActivitySMS.class);
					ActivityMain.this.startActivityForResult(intent, AppConstant.REQUESTCODE_MAIN);
					break;
			}
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		mVLoading.setVisibility(View.GONE);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void OnItemClick(View view, int position) {
		if (mIsActionMode) {
			SMSEntity entity = mListData.get(position);
			entity.setIsCheck(!entity.isCheck());
			mAdapter.notifyDataSetChanged();
			updateBtnText();
		} else {
			Intent intent = new Intent(this, ActivitySpamDetail.class);
			SMSEntity sms = mListData.get(position);
			intent.putExtra(AppConstant.ARGS_SMSENTITY, sms);
			startActivity(intent);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case AppConstant.REQUESTCODE_MAIN:
				switch (resultCode) {
					case AppConstant.RESULTCODE_SMSLOG:
						mVLoading.setVisibility(View.VISIBLE);
						final List<PeopleInfo> infoList = data.getParcelableArrayListExtra(
								AppConstant.ARGS_SELECTLIST);
						Async_Spam_Import asyncTask = new Async_Spam_Import(this, infoList, this);
						asyncTask.execute();
						break;
				}
				break;
		}

	}

	private ActionMode.Callback mCallback = new ActionMode.Callback() {

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			mIsActionMode = true;
			updateBtnText();

			mAdapter.setIsActionMode(true);
			mAdapter.notifyDataSetChanged();
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mIsActionMode = false;
			mTvbtn.setText(R.string.add);

			mAdapter.setIsActionMode(false);
			mAdapter.notifyDataSetChanged();
			for (SMSEntity entity : mListData) {
				entity.setIsCheck(false);
			}
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.actionmenu, menu);
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			boolean ret = false;
			List<SMSEntity> list = new ArrayList<>();
			for (SMSEntity entity : mListData) {
				if (entity.isCheck()) {
					list.add(entity);
				}
			}
			if (item.getItemId() == R.id.actionmode_delete) {
				SpamListManager.getInstance().removeSpam(ActivityMain.this, list);
				mode.finish();
				ret = true;
			} else if (item.getItemId() == R.id.actionmode_recover) {
				Async_Spam_Recover asyncRecover = new Async_Spam_Recover(ActivityMain.this, list);
				asyncRecover.execute();
				SpamListManager.getInstance().removeSpam(ActivityMain.this, list);
				mode.finish();
				ret = true;
			}
			return ret;
		}
	};

	@Override
	public void onItemLongClick(View view, int position) {
		if (!mIsActionMode) {
			mListData.get(position).setIsCheck(true);
			startSupportActionMode(mCallback);
		}
	}

	private void updateBtnText() {
		if (isAllSelect()) {
			mTvbtn.setText(R.string.reset);
		} else {
			mTvbtn.setText(R.string.select_all);
		}
	}

	private boolean isAllSelect() {
		for (SMSEntity entity : mListData) {
			if (!entity.isCheck()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void callBack(List<SMSEntity> list) {
		SpamListManager.getInstance().addSMS(this, list);
	}
}

package android.com.smshelper.activity;

import android.com.smshelper.R;
import android.com.smshelper.adapter.AdapterSpam;
import android.com.smshelper.entity.SMSEntity;
import android.com.smshelper.interfac.OnItemClickListener;
import android.com.smshelper.interfac.OnItemLongClickListener;
import android.com.smshelper.manager.SpamListManager;
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

import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class ActivityMain extends ActionBarActivity implements MenuDrawer.OnDrawerStateChangeListener, View
		.OnClickListener, Observer, OnItemClickListener, OnItemLongClickListener {
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
		}
	}

	private ActionMode.Callback mCallback = new ActionMode.Callback() {

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			mIsActionMode = true;
			mTvbtn.setText(R.string.select_all);

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
			if (item.getItemId() == R.id.actionmode_delete) {
				mode.finish();
				ret = true;
			}
			return ret;
		}
	};

	@Override
	public void onItemLongClick(View view, int position) {
		if (!mIsActionMode) {
			startSupportActionMode(mCallback);
			mListData.get(position).setIsCheck(true);
		}
	}
}

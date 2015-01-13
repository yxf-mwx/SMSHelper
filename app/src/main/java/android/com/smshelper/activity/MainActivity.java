package android.com.smshelper.activity;

import android.com.smshelper.R;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;


public class MainActivity extends ActionBarActivity implements MenuDrawer.OnDrawerStateChangeListener {
	MenuDrawer mMenuDrawer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActionBar();
		initMenuDrawer();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
				MainActivity.this,
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
}

package android.com.smshelper.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.com.smshelper.activity.CallLogsActivity;
import android.com.smshelper.interfac.ManualInputCallback;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by admin on 15-1-18.
 */
public class DialogFragment_AddList extends DialogFragment {
	private Context mContext;
	private ManualInputCallback mCallback;
	private static String BLACKLIST = "黑名单管理";
	private static String WHITELIST = "白名单管理";
	private static String ARGS_TITLE = "title";
	private String mTitle;

	public static DialogFragment_AddList newInstance(ManualInputCallback callback, int type) {
		DialogFragment_AddList fragment = new DialogFragment_AddList();
		fragment.setCallback(callback);
		Bundle args = new Bundle();
		if (type == 1) {
			args.putString(ARGS_TITLE, BLACKLIST);
		} else if (type == 2) {
			args.putString(ARGS_TITLE, WHITELIST);
		}
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		Bundle args = getArguments();
		if (args != null) {
			mTitle = args.getString(ARGS_TITLE);
		}
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(mTitle)
				.setItems(new String[]{
								"手工输入",
								"从通话记录添加",
								"从短信记录添加",
								"从通讯录添加"
						},
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								switch (which) {
									case 0:
										DialogFragment_ManualInput fragment = DialogFragment_ManualInput.newInstance
												(mCallback);
										fragment.show(getFragmentManager(), "ManualInput");
										return;
									case 1:
										Intent intent = new Intent(mContext, CallLogsActivity.class);
										mContext.startActivity(intent);
										return;
								}
							}
						});
		AlertDialog alertDialog = builder.create();
		return alertDialog;
	}

	public void setCallback(ManualInputCallback callback) {
		mCallback = callback;
	}
}

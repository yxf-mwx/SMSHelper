package android.com.smshelper.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.com.smshelper.R;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.interfac.ManualInputCallback;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15-1-14.
 */
public class DialogFragment_ManualInput extends DialogFragment {
	private Context mContext;
	private EditText mEdtPhone;
	private EditText mEdtName;
	private ManualInputCallback mManualInputCallback;

	public static DialogFragment_ManualInput newInstance(ManualInputCallback callback) {
		DialogFragment_ManualInput fragment = new DialogFragment_ManualInput();
		fragment.setManualInputCallback(callback);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		View view = View.inflate(mContext, R.layout.dialogfragment_manualinput, null);
		mEdtName = (EditText) view.findViewById(R.id.edt_inputname_dialogfragment);
		mEdtPhone = (EditText) view.findViewById(R.id.edt_inputphone_dialogfragment);
		builder.setTitle("手动输入")
				.setView(view)
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final String phone = mEdtPhone.getText().toString();
						final String name = mEdtName.getText().toString();
						if (TextUtils.isEmpty(phone)) {
							Toast.makeText(mContext, "电话号码不能为空！", Toast.LENGTH_LONG).show();
							return;
						}
						if (mManualInputCallback != null) {
							PeopleInfo peopleInfo = new PeopleInfo(name, phone);
							List<PeopleInfo> infoList = new ArrayList<PeopleInfo>();
							infoList.add(peopleInfo);
							mManualInputCallback.callback(infoList);
						}
						DialogFragment_ManualInput.this.dismiss();
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						DialogFragment_ManualInput.this.dismiss();
					}
				});
		AlertDialog alertDialog = builder.create();
		try {

			Field field = alertDialog.getClass().getDeclaredField("mAlert");
			field.setAccessible(true);
			//   获得mAlert变量的值
			Object obj = field.get(alertDialog);
			field = obj.getClass().getDeclaredField("mHandler");
			field.setAccessible(true);
			//   修改mHandler变量的值，使用新的ButtonHandler类
			field.set(obj, new Handler());
		} catch (Exception e) {
		}
		return alertDialog;
	}

	public void setManualInputCallback(ManualInputCallback manualInputCallback) {
		mManualInputCallback = manualInputCallback;
	}

	@Override
	public void dismiss() {
		super.dismiss();

	}
}

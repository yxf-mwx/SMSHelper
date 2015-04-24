package android.com.smshelper.fragment;

import android.com.smshelper.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author yxf
 * @date 15-4-23
 * @time 8:40
 * updater xxx
 * update  yy-MM-dd
 * @comment balabalabala
 */
public class DialogFragment_Keyword extends DialogFragment implements View.OnClickListener {
	private String mOriginal;
	private String mNewKeyword;
	private EditText mEdtKeyword;
	CallBack mCallBack;

	public static DialogFragment_Keyword newInstance(CallBack callBack) {
		DialogFragment_Keyword fragment = new DialogFragment_Keyword();
		fragment.setCallBack(callBack);
		return fragment;
	}

	public static DialogFragment_Keyword newInstance(CallBack callBack, String original) {
		DialogFragment_Keyword fragment_keyword = DialogFragment_Keyword.newInstance(callBack);
		fragment_keyword.setOriginal(original);
		return fragment_keyword;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
			savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_keyword_input, container);
		mEdtKeyword = (EditText) rootView.findViewById(R.id.edt_keyword_input);
		rootView.findViewById(R.id.tv_input_positive).setOnClickListener(this);
		rootView.findViewById(R.id.tv_input_negetive).setOnClickListener(this);
		if (TextUtils.isEmpty(mOriginal)) {
			getDialog().setTitle(getString(R.string.add_keyword));
		} else {
			getDialog().setTitle(getString(R.string.correct_keyword));
			mEdtKeyword.setText(mOriginal);
			mEdtKeyword.setSelection(mOriginal.length());
		}
		return rootView;
	}

	public void setCallBack(CallBack callBack) {
		mCallBack = callBack;
	}

	public void setOriginal(String original) {
		mOriginal = original;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_input_positive:
				mNewKeyword = mEdtKeyword.getText().toString();
				if (TextUtils.isEmpty(mNewKeyword)) {
					Toast.makeText(getActivity(), "关键字不能为空", Toast.LENGTH_SHORT).show();
				}
				if (mCallBack != null) {
					mCallBack.callback(mNewKeyword, mOriginal);
				}
				break;
			case R.id.tv_input_negetive:
				dismiss();
				break;
		}
	}

	public static interface CallBack {
		public void callback(String keyword, String original);
	}

}

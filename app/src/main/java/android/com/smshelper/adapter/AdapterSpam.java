package android.com.smshelper.adapter;

import android.com.smshelper.AppConstant;
import android.com.smshelper.R;
import android.com.smshelper.Util.DateUtils;
import android.com.smshelper.entity.SMSEntity;
import android.com.smshelper.interfac.OnItemClickListener;
import android.com.smshelper.interfac.OnItemLongClickListener;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * @author yxf
 * @date 5/2/15
 * @time 1:16 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 垃圾短信的适配器类
 */
public class AdapterSpam extends BaseAdapter implements View.OnLongClickListener, View.OnClickListener {
	private Context mContext;
	private List<SMSEntity> mListData;
	private OnItemClickListener mListener;
	private OnItemLongClickListener mLongClickListener;
	private boolean mIsActionMode;

	public AdapterSpam(Context context, List<SMSEntity> listData, OnItemClickListener listener,
	                   OnItemLongClickListener longClickListener) {
		mContext = context;
		mListData = listData;
		mListener = listener;
		mLongClickListener = longClickListener;
		mIsActionMode = false;
	}

	@Override
	public int getCount() {
		return mListData.size();
	}

	@Override
	public Object getItem(int position) {
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.listitem_spam, null);
			holder.number = (TextView) convertView.findViewById(R.id.tv_listitem_spam_number);
			holder.date = (TextView) convertView.findViewById(R.id.tv_listitem_spam_date);
			holder.content = (TextView) convertView.findViewById(R.id.tv_listitem_spam_content);
			holder.checkbox = (CheckBox) convertView.findViewById(R.id.cb_listitem_spam);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		SMSEntity entity = mListData.get(position);
		final String number = entity.getAddress();
		holder.number.setText(number);
		final long date = entity.getDate();
		final String dateStr = DateUtils.getDate(new Date(date));
		holder.date.setText(dateStr);
		final String content = entity.getBody();
		holder.content.setText(content);
		convertView.setOnLongClickListener(this);
		convertView.setOnClickListener(this);
		convertView.setTag(AppConstant.TAG_POSTION, position);
		holder.checkbox.setClickable(false);
		if (mIsActionMode) {
			holder.checkbox.setVisibility(View.VISIBLE);
			holder.checkbox.setChecked(entity.isCheck());
		} else {
			holder.checkbox.setVisibility(View.GONE);
		}
		return convertView;
	}

	@Override
	public boolean onLongClick(View v) {
		final Object obj = v.getTag(AppConstant.TAG_POSTION);
		if (obj != null) {
			final int position = (Integer) obj;
			if (mLongClickListener != null) {
				mLongClickListener.onItemLongClick(v, position);
				return true;
			}
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		final Object obj = v.getTag(AppConstant.TAG_POSTION);
		if (obj != null) {
			final int position = (Integer) obj;
			if (mListener != null) {
				mListener.OnItemClick(v, position);
			}
		}
	}

	public boolean isActionMode() {
		return mIsActionMode;
	}

	public void setIsActionMode(boolean isActionMode) {
		mIsActionMode = isActionMode;
	}

	private static class ViewHolder {
		TextView number;
		TextView date;
		TextView content;
		CheckBox checkbox;
	}
}

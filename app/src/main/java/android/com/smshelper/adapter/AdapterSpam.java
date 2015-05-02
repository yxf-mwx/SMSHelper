package android.com.smshelper.adapter;

import android.com.smshelper.R;
import android.com.smshelper.Util.DateUtils;
import android.com.smshelper.entity.SMSEntity;
import android.com.smshelper.interfac.OnItemClickListener;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class AdapterSpam extends BaseAdapter {
	private Context mContext;
	private List<SMSEntity> mListData;
	private OnItemClickListener mListener;

	public AdapterSpam(Context context, List<SMSEntity> listData, OnItemClickListener listener) {
		mContext = context;
		mListData = listData;
		mListener = listener;
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
		return convertView;
	}

	private static class ViewHolder {
		TextView number;
		TextView date;
		TextView content;
	}
}

package android.com.smshelper.adapter;

import android.com.smshelper.R;
import android.com.smshelper.Util.DateUtils;
import android.com.smshelper.entity.ListItemSMS;
import android.com.smshelper.interfac.OnItemClickListener;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by yxf on 15-4-7.
 */
public class AdapterSMS extends BaseAdapter {
	private Context mContext;
	private List<ListItemSMS> mListData;
	private OnItemClickListener mListener;

	public AdapterSMS(Context context, List<ListItemSMS> listData, OnItemClickListener listener) {
		mListData = listData;
		mListener = listener;
		mContext = context;
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
			convertView = View.inflate(mContext, R.layout.listitem_sms, null);
			holder.checkbox = (CheckBox) convertView.findViewById(R.id.cb_sms_checkbox);
			holder.body = (TextView) convertView.findViewById(R.id.tv_sms_body);
			holder.personOrAddress = (TextView) convertView.findViewById(R.id.tv_sms_addressorperson);
			holder.date = (TextView) convertView.findViewById(R.id.tv_sms_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ListItemSMS item = mListData.get(position);
		final String person = item.getPersion();
		final String address = item.getAddress();
		if (TextUtils.isEmpty(person)) {
			holder.personOrAddress.setText(address);
		} else {
			holder.personOrAddress.setText(person);
		}

		final Date date = new Date(item.getDate());
		String dateStr = DateUtils.getDate(date);
		holder.date.setText(dateStr);
		holder.body.setText(item.getBody());
		return convertView;
	}

	private static class ViewHolder {
		TextView personOrAddress;
		TextView date;
		TextView body;
		CheckBox checkbox;
	}
}

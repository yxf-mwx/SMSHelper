package android.com.smshelper.adapter;

import android.com.smshelper.AppConstant;
import android.com.smshelper.R;
import android.com.smshelper.util.DateUtils;
import android.com.smshelper.entity.CallLogs;
import android.content.Context;
import android.provider.CallLog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 15-1-20.
 */
public class AdapterCallLogs extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
	List<CallLogs> mList;
	private Context mContext;

	public AdapterCallLogs(Context context, List<CallLogs> list) {
		mList = list;
		mContext = context;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
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
			convertView = View.inflate(mContext, R.layout.listitem_calllog, null);
			holder.contact = (TextView) convertView.findViewById(R.id.tv_contacts_calllog);
			holder.date = (TextView) convertView.findViewById(R.id.tv_date_calllog);
			holder.type = (TextView) convertView.findViewById(R.id.tv_type_calllog);
			holder.mobilearea = (TextView) convertView.findViewById(R.id.tv_mobilearea_calllog);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_check_callog);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final CallLogs log = mList.get(position);
		if (log != null) {
			final String phone = log.getPhone();
			final String name = log.getName();
			if (TextUtils.isEmpty(name)) {
				holder.contact.setText(phone);
			} else {
				holder.contact.setText(name);
			}

			final Date date = new Date(log.getDate());
			String dateStr = DateUtils.getDate(date);
			holder.date.setText(dateStr);

			final int type = log.getType();
			String typeStr = "";
			switch (type) {
				case CallLog.Calls.INCOMING_TYPE:
					typeStr = "来电";
					break;
				case CallLog.Calls.OUTGOING_TYPE:
					typeStr = "去电";
					break;
				case CallLog.Calls.MISSED_TYPE:
					typeStr = "未接";
			}
			holder.type.setText(typeStr);
			holder.mobilearea.setText(log.getNumArea());
			final boolean isCheck = log.isCheck();
			holder.checkBox.setTag(AppConstant.TAG_POSTION, position);
			holder.checkBox.setOnCheckedChangeListener(this);
			holder.checkBox.setChecked(isCheck);
		}
		return convertView;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		final Object obj = buttonView.getTag(AppConstant.TAG_POSTION);
		if (obj != null) {
			int position = (Integer) obj;
			CallLogs log = mList.get(position);
			log.setCheck(isChecked);
			notifyDataSetChanged();
		}
	}

	private static class ViewHolder {
		TextView contact;
		TextView date;
		TextView type;
		TextView mobilearea;
		CheckBox checkBox;
	}

}

package android.com.smshelper.adapter;

import android.com.smshelper.AppConstant;
import android.com.smshelper.R;
import android.com.smshelper.entity.PeopleInfo;
import android.com.smshelper.manager.WhiteListManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin on 15-1-18.
 */
public class AdapterWhiteList extends BaseAdapter implements View.OnClickListener {
	private List<PeopleInfo> mList;
	private Context mContext;

	public AdapterWhiteList(Context context, List<PeopleInfo> list) {
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
			convertView = View.inflate(mContext, R.layout.listitem_blacklist, null);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name_blacklist);
			holder.number = (TextView) convertView.findViewById(R.id.tv_number_blacklist);
			holder.btnDelete = (TextView) convertView.findViewById(R.id.btn_delete_blacklist);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final PeopleInfo info = mList.get(position);
		final String name = info.getName();
		final String number = info.getPhone();
		holder.name.setText(name);
		holder.number.setText(number);
		holder.btnDelete.setTag(AppConstant.TAG_POSTION, position);
		holder.btnDelete.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		Object obj = v.getTag(AppConstant.TAG_POSTION);
		if (obj != null) {
			final int position = (Integer) obj;
			WhiteListManager.getInstance(mContext).delInfo(position);
			notifyDataSetChanged();
		}
	}

	private static class ViewHolder {
		TextView name;
		TextView number;
		TextView btnDelete;
	}
}

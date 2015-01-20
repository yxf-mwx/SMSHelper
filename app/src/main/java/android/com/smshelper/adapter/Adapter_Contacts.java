package android.com.smshelper.adapter;

import android.com.smshelper.entity.PeopleInfo;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by admin on 15-1-19.
 */
public class Adapter_Contacts extends BaseAdapter {
	private Context mContext;
	private List<PeopleInfo> mList;

	public Adapter_Contacts(Context context, List<PeopleInfo> list) {
		mContext = context;
		mList = list;
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
		return convertView;
	}

	private static class ViewHolder {
	}
}

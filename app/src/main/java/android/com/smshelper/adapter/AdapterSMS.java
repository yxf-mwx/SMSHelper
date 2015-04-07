package android.com.smshelper.adapter;

import android.com.smshelper.R;
import android.com.smshelper.entity.ListItemSMS;
import android.com.smshelper.interfac.OnItemClickListener;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

	private static class ViewHolder {

	}
}

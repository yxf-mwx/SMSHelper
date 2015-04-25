package android.com.smshelper.adapter;

import android.com.smshelper.AppConstant;
import android.com.smshelper.R;
import android.com.smshelper.interfac.OnItemClickListener;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author yxf
 * @date 15-4-21
 * @time ??9:36
 * updater xxx
 * update  yy-MM-dd
 * @comment 关键词界面类的适配器
 */
public class AdapterKeyWord extends BaseAdapter implements View.OnClickListener {
	private Context mContext;
	private List<String> mListData;
	private OnItemClickListener mListener;

	public AdapterKeyWord(Context context, List<String> list, OnItemClickListener listener) {
		mContext = context;
		mListData = list;
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
			convertView = View.inflate(mContext, R.layout.listitem_keyword, null);
			holder.keyword = (TextView) convertView.findViewById(R.id.tv_keyword_word);
			holder.delete = (TextView) convertView.findViewById(R.id.tv_keyword_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final String keyword = mListData.get(position);
		holder.keyword.setText(keyword);

		holder.delete.setOnClickListener(this);
		holder.delete.setTag(AppConstant.TAG_POSTION, position);

		convertView.setOnClickListener(this);
		convertView.setTag(AppConstant.TAG_POSTION, position);

		return convertView;
	}

	@Override
	public void onClick(View v) {
		final Object obj = v.getTag(AppConstant.TAG_POSTION);
		if (obj instanceof Integer) {
			final int position = (Integer) obj;
			if (mListener != null) {
				mListener.OnItemClick(v, position);
			}
		}
	}

	private static class ViewHolder {
		TextView keyword;
		TextView delete;
	}
}

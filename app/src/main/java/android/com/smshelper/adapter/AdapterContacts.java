package android.com.smshelper.adapter;

import android.com.smshelper.AppConstant;
import android.com.smshelper.R;
import android.com.smshelper.entity.Contact;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin on 15-1-19.
 */
public class AdapterContacts extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
	private Context mContext;
	private List<Contact> mList;

	public AdapterContacts(Context context, List<Contact> list) {
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
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.listitem_contacts, null);
			holder.nick = (TextView) convertView.findViewById(R.id.tv_contacts_listitem_nick);
			holder.phone = (TextView) convertView.findViewById(R.id.tv_contacts_listitem_phone);
			holder.check = (CheckBox) convertView.findViewById(R.id.cb_contacts_listitem_checkbox);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Contact contact = mList.get(position);
		if (contact != null) {
			String nick = contact.getNick();
			String phone = contact.getPhone();
			boolean isCheck = contact.isCheck();
			holder.nick.setText(nick);
			holder.phone.setText(phone);
			holder.check.setTag(AppConstant.TAG_POSTION, position);
			holder.check.setOnCheckedChangeListener(this);
			holder.check.setChecked(isCheck);
		}
		return convertView;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Object obj = buttonView.getTag(AppConstant.TAG_POSTION);
		if (obj != null) {
			int position = (Integer) obj;
			Contact contact = mList.get(position);
			contact.setCheck(isChecked);
			notifyDataSetChanged();
		}
	}

	private static class ViewHolder {
		TextView nick;
		TextView phone;
		CheckBox check;
	}
}

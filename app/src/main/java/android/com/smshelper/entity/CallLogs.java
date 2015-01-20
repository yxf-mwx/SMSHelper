package android.com.smshelper.entity;

import java.util.Date;

/**
 * Created by admin on 15-1-20.
 */
public class CallLogs {
	private String mPhone;
	private String mName;
	private Date mDate;
	private String mAddress;
	private String mType;
	private boolean isCheck;

	public CallLogs(String phone, String name, Date date, String address, String type) {
		mPhone = phone;
		mName = name;
		mDate = date;
		mAddress = address;
		mType = type;
		isCheck = false;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(String phone) {
		mPhone = phone;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		mAddress = address;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getType() {
		return mType;
	}

	public void setType(String type) {
		mType = type;
	}
}

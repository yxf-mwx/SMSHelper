package android.com.smshelper.entity;

/**
 * Created by yxf on 15-4-7.
 */
public class ListItemSMS {
	//?????????null
	private String mPersion;
	//?????
	private String mAddress;
	//????
	private String mBody;
	//????
	private long mDate;
	//?????
	private boolean mCheck;

	public String getPersion() {
		return mPersion;
	}

	public void setPersion(String persion) {
		mPersion = persion;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		mAddress = address;
	}

	public String getBody() {
		return mBody;
	}

	public void setBody(String body) {
		mBody = body;
	}

	public long getDate() {
		return mDate;
	}

	public void setDate(long date) {
		mDate = date;
	}

	public boolean isCheck() {
		return mCheck;
	}

	public void setCheck(boolean check) {
		mCheck = check;
	}
}

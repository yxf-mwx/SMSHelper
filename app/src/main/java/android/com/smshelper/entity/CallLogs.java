package android.com.smshelper.entity;

/**
 * Created by admin on 15-1-20.
 */
public class CallLogs {
	private String mPhone;
	private String mName;
	private long mDate;
	private int mType;
	private String mNumArea;
	private String mNumType;
	private boolean isCheck;

	public CallLogs(String phone, String name, long date, int type, String numArea, String numType) {
		mPhone = phone;
		mName = name;
		mDate = date;
		mType = type;
		mNumArea = numArea;
		mNumType = numType;
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

	public long getDate() {
		return mDate;
	}

	public void setDate(long date) {
		mDate = date;
	}

	public int getType() {
		return mType;
	}

	public void setType(int type) {
		mType = type;
	}

	public String getNumArea() {
		return mNumArea;
	}

	public void setNumArea(String numArea) {
		mNumArea = numArea;
	}

	public String getNumType() {
		return mNumType;
	}

	public void setNumType(String numType) {
		mNumType = numType;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	@Override
	public String toString() {
		return "CallLogs{" +
				"mPhone='" + mPhone + '\'' +
				", mName='" + mName + '\'' +
				", mDate=" + mDate +
				", mType=" + mType +
				", mNumArea='" + mNumArea + '\'' +
				", mNumType='" + mNumType + '\'' +
				", isCheck=" + isCheck +
				'}';
	}
}

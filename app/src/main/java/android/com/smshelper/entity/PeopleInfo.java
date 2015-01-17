package android.com.smshelper.entity;

/**
 * Created by admin on 15-1-14.
 */
public class PeopleInfo {
	private String mName;
	private String mPhone;

	public PeopleInfo(String name, String phone) {
		mName = name;
		mPhone = phone;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(String phone) {
		mPhone = phone;
	}

	@Override
	public String toString() {
		return "PeopleInfo{" +
				"mName='" + mName + '\'' +
				", mPhone='" + mPhone + '\'' +
				'}';
	}
}

package android.com.smshelper.entity;

/**
 * Created by admin on 15-1-14.
 */
public class PeopleInfo {
	private String mName;
	private String mNumber;

	public PeopleInfo(String name, String number) {
		mName = name;
		mNumber = number;
	}

	public PeopleInfo() {
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getNumber() {
		return mNumber;
	}

	public void setNumber(String number) {
		mNumber = number;
	}
}

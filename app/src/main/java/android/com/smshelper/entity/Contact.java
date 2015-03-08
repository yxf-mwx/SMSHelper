package android.com.smshelper.entity;

/**
 * Created by yxf on 15-3-8.
 */
public class Contact {
	private String mNick;
	private String mPhone;
	private boolean mCheck;

	public Contact(String nick, String phone) {
		mNick = nick;
		mPhone = phone;
		mCheck = false;
	}

	public boolean isCheck() {
		return mCheck;
	}

	public void setCheck(boolean check) {
		mCheck = check;
	}

	public String getNick() {
		return mNick;
	}

	public void setNick(String nick) {
		mNick = nick;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(String phone) {
		mPhone = phone;
	}
}

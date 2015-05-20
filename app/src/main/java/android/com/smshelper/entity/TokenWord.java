package android.com.smshelper.entity;

/**
 * @author yxf
 * @date 5/10/15
 * @time 2:42 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment balabalabala
 */
public class TokenWord {
	private String mToken;
	private int mSpam;
	private int mNonSpam;

	public TokenWord() {
	}

	public TokenWord(String token, int spam, int nonSpam) {
		mToken = token;
		mSpam = spam;
		mNonSpam = nonSpam;
	}

	public String getToken() {
		return mToken;
	}

	public void setToken(String token) {
		mToken = token;
	}

	public int getSpam() {
		return mSpam;
	}

	public void setSpam(int spam) {
		mSpam = spam;
	}

	public int getNonSpam() {
		return mNonSpam;
	}

	public void setNonSpam(int nonSpam) {
		mNonSpam = nonSpam;
	}

	@Override
	public String toString() {
		return "TokenWord{" +
				"mToken='" + mToken + '\'' +
				", mSpam=" + mSpam +
				", mNonSpam=" + mNonSpam +
				'}';
	}
}

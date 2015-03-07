package android.com.smshelper.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 15-1-14.
 */
public class PeopleInfo implements Parcelable {
	private String mName;
	private String mPhone;

	public PeopleInfo(String name, String phone) {
		mName = name;
		mPhone = phone;
	}

	private PeopleInfo(Parcel in) {
		mName = in.readString();
		mPhone = in.readString();
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mName);
		dest.writeString(mPhone);
	}

	public static final Creator<PeopleInfo> CREATOR = new Creator<PeopleInfo>() {
		@Override
		public PeopleInfo createFromParcel(Parcel source) {
			return new PeopleInfo(source);
		}

		@Override
		public PeopleInfo[] newArray(int size) {
			return new PeopleInfo[size];
		}
	};
}

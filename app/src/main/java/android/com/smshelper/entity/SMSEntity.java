package android.com.smshelper.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author yxf
 * @date 4/29/15
 * @time 10:02 PM
 * updater xxx
 * update  yy-MM-dd
 * @comment 短信息的实体类
 */
public class SMSEntity implements Parcelable {
	private int mErrorCode;
	private String mBody;
	private String mAddress;
	private long mDate;
	private long mDateSent;
	private int mProtocol;
	private boolean mRead;
	private int mSeen;
	private String mSubject;
	private boolean mReplyPathPresent;
	private String mTextCenter;
	private long mThreadId;
	private String mServiceCenter;

	public int getErrorCode() {
		return mErrorCode;
	}

	public void setErrorCode(int errorCode) {
		mErrorCode = errorCode;
	}

	public String getBody() {
		return mBody;
	}

	public void setBody(String body) {
		mBody = body;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		mAddress = address;
	}

	public long getDate() {
		return mDate;
	}

	public void setDate(long date) {
		mDate = date;
	}

	public long getDateSent() {
		return mDateSent;
	}

	public void setDateSent(long dateSent) {
		mDateSent = dateSent;
	}

	public int getProtocol() {
		return mProtocol;
	}

	public void setProtocol(int protocol) {
		mProtocol = protocol;
	}

	public boolean isRead() {
		return mRead;
	}

	public void setRead(boolean read) {
		mRead = read;
	}

	public int getSeen() {
		return mSeen;
	}

	public void setSeen(int seen) {
		mSeen = seen;
	}

	public String getSubject() {
		return mSubject;
	}

	public void setSubject(String subject) {
		mSubject = subject;
	}

	public boolean isReplyPathPresent() {
		return mReplyPathPresent;
	}

	public void setReplyPathPresent(boolean replyPathPresent) {
		mReplyPathPresent = replyPathPresent;
	}

	public String getTextCenter() {
		return mTextCenter;
	}

	public void setTextCenter(String textCenter) {
		mTextCenter = textCenter;
	}

	public long getThreadId() {
		return mThreadId;
	}

	public void setThreadId(long threadId) {
		this.mThreadId = threadId;
	}

	public String getServiceCenter() {
		return mServiceCenter;
	}

	public void setServiceCenter(String serviceCenter) {
		mServiceCenter = serviceCenter;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.mErrorCode);
		dest.writeString(this.mBody);
		dest.writeString(this.mAddress);
		dest.writeLong(this.mDate);
		dest.writeLong(this.mDateSent);
		dest.writeInt(this.mProtocol);
		dest.writeByte(mRead ? (byte) 1 : (byte) 0);
		dest.writeInt(this.mSeen);
		dest.writeString(this.mSubject);
		dest.writeByte(mReplyPathPresent ? (byte) 1 : (byte) 0);
		dest.writeString(this.mTextCenter);
		dest.writeLong(this.mThreadId);
		dest.writeString(this.mServiceCenter);
	}

	public SMSEntity() {
	}

	private SMSEntity(Parcel in) {
		this.mErrorCode = in.readInt();
		this.mBody = in.readString();
		this.mAddress = in.readString();
		this.mDate = in.readLong();
		this.mDateSent = in.readLong();
		this.mProtocol = in.readInt();
		this.mRead = in.readByte() != 0;
		this.mSeen = in.readInt();
		this.mSubject = in.readString();
		this.mReplyPathPresent = in.readByte() != 0;
		this.mTextCenter = in.readString();
		this.mThreadId = in.readLong();
		this.mServiceCenter = in.readString();
	}

	public static final Parcelable.Creator<SMSEntity> CREATOR = new Parcelable.Creator<SMSEntity>() {
		public SMSEntity createFromParcel(Parcel source) {
			return new SMSEntity(source);
		}

		public SMSEntity[] newArray(int size) {
			return new SMSEntity[size];
		}
	};

	@Override
	public String toString() {
		return "SMSEntity{" +
				"mErrorCode=" + mErrorCode +
				", mBody='" + mBody + '\'' +
				", mAddress='" + mAddress + '\'' +
				", mDate=" + mDate +
				", mDateSent=" + mDateSent +
				", mProtocol=" + mProtocol +
				", mRead=" + mRead +
				", mSeen=" + mSeen +
				", mSubject='" + mSubject + '\'' +
				", mReplyPathPresent=" + mReplyPathPresent +
				", mTextCenter='" + mTextCenter + '\'' +
				", mThreadId=" + mThreadId +
				", mServiceCenter='" + mServiceCenter + '\'' +
				'}';
	}
}

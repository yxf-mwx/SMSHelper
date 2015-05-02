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
	private boolean mSeen;
	private String mSubject;
	private boolean mReplyPathPresent;
	private long mThreadId;
	private String mServiceCenter;
	private boolean mIsCheck;

	public SMSEntity(int errorCode, String body, String address, long date, long dateSent, int protocol, boolean read,
	                 boolean seen, String subject, boolean replyPathPresent, long threadId, String serviceCenter) {
		mErrorCode = errorCode;
		mBody = body;
		mAddress = address;
		mDate = date;
		mDateSent = dateSent;
		mProtocol = protocol;
		mRead = read;
		mSeen = seen;
		mSubject = subject;
		mReplyPathPresent = replyPathPresent;
		mThreadId = threadId;
		mServiceCenter = serviceCenter;
		mIsCheck = false;
	}

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

	public boolean isSeen() {
		return mSeen;
	}

	public void setSeen(boolean seen) {
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

	public void setIsCheck(boolean isCheck) {
		mIsCheck = isCheck;
	}

	public boolean isCheck() {
		return mIsCheck;
	}

	public SMSEntity() {
		mIsCheck = false;
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
		dest.writeByte(mSeen ? (byte) 1 : (byte) 0);
		dest.writeString(this.mSubject);
		dest.writeByte(mReplyPathPresent ? (byte) 1 : (byte) 0);
		dest.writeLong(this.mThreadId);
		dest.writeString(this.mServiceCenter);
	}

	private SMSEntity(Parcel in) {
		this.mErrorCode = in.readInt();
		this.mBody = in.readString();
		this.mAddress = in.readString();
		this.mDate = in.readLong();
		this.mDateSent = in.readLong();
		this.mProtocol = in.readInt();
		this.mRead = in.readByte() != 0;
		this.mSeen = in.readInt() != 0;
		this.mSubject = in.readString();
		this.mReplyPathPresent = in.readByte() != 0;
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
				", mThreadId=" + mThreadId +
				", mServiceCenter='" + mServiceCenter + '\'' +
				'}';
	}
}

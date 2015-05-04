package android.com.smshelper.reciever;

import android.com.smshelper.classify.ClassifyCenter;
import android.com.smshelper.entity.SMSEntity;
import android.com.smshelper.manager.SpamListManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15-1-9.
 * comment 短信接受处理类
 */
public class SmsReciever extends BroadcastReceiver {
	private final static String ACTION_SMS_RECIEVE = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (ACTION_SMS_RECIEVE.equals(action)) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdusObj = (Object[]) bundle.get("pdus");
				SMSEntity entity = createEntityFromSms(pdusObj);
				final String body = entity.getBody();
				final String address = entity.getAddress();

				//用分类器分辨是否为垃圾短信，如果是做处理，不是就不需要处理
				if (ClassifyCenter.getInstance(context).classify(body, address) == ClassifyCenter.SPAM) {
					List<SMSEntity> list = new ArrayList<>();
					list.add(entity);
					SpamListManager.getInstance().addSMS(context, list);
					abortBroadcast();
				}

			}
		}
	}

	private SMSEntity createEntityFromSms(Object[] pdusObj) {
		SMSEntity sms = new SMSEntity();
		SmsMessage msg = SmsMessage.createFromPdu(((byte[]) pdusObj[0]));
		sms.setAddress(msg.getOriginatingAddress());
		long now = System.currentTimeMillis();
		long buildDate = 0;
		if (now < buildDate) {
			now = msg.getTimestampMillis();
		}
		sms.setDate(now);
		sms.setDateSent(msg.getTimestampMillis());
		sms.setProtocol(msg.getProtocolIdentifier());
		sms.setRead(false);
		sms.setSeen(false);
		if (msg.getPseudoSubject().length() > 0) {
			sms.setSubject(msg.getPseudoSubject());
		}
		sms.setReplyPathPresent(msg.isReplyPathPresent());
		sms.setServiceCenter(msg.getServiceCenterAddress());

		StringBuffer sb = new StringBuffer();
		if (pdusObj.length > 0) {
			for (int i = 0; i < pdusObj.length; i++) {
				msg = SmsMessage.createFromPdu(((byte[]) pdusObj[i]));
				sb.append(msg.getDisplayMessageBody());
			}
		}
		sms.setBody(sb.toString());
		return sms;
	}
}

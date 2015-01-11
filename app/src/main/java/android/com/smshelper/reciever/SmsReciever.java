package android.com.smshelper.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 15-1-9.
 */
public class SmsReciever extends BroadcastReceiver {
	private final static String ACTION_SMS_RECIEVE = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (ACTION_SMS_RECIEVE.equals(action)) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				SmsMessage msg = null;
				Object[] pdusObj = (Object[]) bundle.get("pdus");
				for (Object p : pdusObj) {
					msg = SmsMessage.createFromPdu(((byte[]) p));
					String msgText = msg.getMessageBody();
					Date date = new Date(msg.getTimestampMillis());
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String receiveTime = format.format(date);
					String senderNumber = msg.getOriginatingAddress();
					Log.d("SMS_RECEIVER", msgText);
				}
			}
		}
	}
}

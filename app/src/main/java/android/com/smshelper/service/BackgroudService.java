package android.com.smshelper.service;

import android.app.Service;
import android.com.smshelper.AppConstant;
import android.com.smshelper.entity.SMSEntity;
import android.com.smshelper.manager.SpamListManager;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yxf
 * @date 15-4-26
 * @time 下午5:41
 * updater xxx
 * update  yy-MM-dd
 * @comment 短信过滤类
 */
public class BackgroudService extends Service {
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final SMSEntity sms = intent.getExtras().getParcelable(AppConstant.ARGS_SMSENTITY);
		final String body = sms.getBody();
		final String address = sms.getAddress();

		//todo 分类

		List<SMSEntity> list = new ArrayList<>();
		list.add(sms);
		SpamListManager.getInstance().addSMS(this, list);
		return super.onStartCommand(intent, flags, startId);
	}
}

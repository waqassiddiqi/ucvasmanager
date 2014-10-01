package net.waqassiddiqi.app.introme.listener;

import net.waqassiddiqi.app.introme.Constants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class TextMessageListener extends BroadcastReceiver {

	static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				for (SmsMessage message : messages) {
					String strFrom = message.getDisplayOriginatingAddress();
					String strMsg = message.getDisplayMessageBody();
					
					Log.d(Constants.TAG, strMsg);
					
					if(strFrom.equals(Constants.SHORT_CODE)) {
						abortBroadcast();
					}
				}
			}
		}
	}
}
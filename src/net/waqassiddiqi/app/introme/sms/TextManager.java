package net.waqassiddiqi.app.introme.sms;

import net.waqassiddiqi.app.introme.Constants;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class TextManager {
	public static void sendSms(String shortCode, String commandText) {
		SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(shortCode, null, commandText, null, null);
	}
	
	public static String parseTextMessage(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");
			SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			}
			for (SmsMessage message : messages) {
				String strFrom = message.getDisplayOriginatingAddress();
				
				if(strFrom.equals(Constants.SHORT_CODE)) {
					return message.getDisplayMessageBody();
				}
			}
		}
		
		return null;
	}
}

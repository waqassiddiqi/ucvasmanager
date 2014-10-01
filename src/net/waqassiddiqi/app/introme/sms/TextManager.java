package net.waqassiddiqi.app.introme.sms;

import android.telephony.SmsManager;

public class TextManager {
	public static void sendSms(String shortCode, String commandText) {
		SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(shortCode, null, commandText, null, null);
	}
}

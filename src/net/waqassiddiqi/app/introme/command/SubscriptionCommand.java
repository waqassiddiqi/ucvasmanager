package net.waqassiddiqi.app.introme.command;

import net.waqassiddiqi.app.introme.Constants;
import net.waqassiddiqi.app.introme.sms.TextManager;

public class SubscriptionCommand implements Command {
	private String commandText = "SUB";
	
	public void execute() {
		TextManager.sendSms(Constants.SHORT_CODE, this.commandText);
	}
}

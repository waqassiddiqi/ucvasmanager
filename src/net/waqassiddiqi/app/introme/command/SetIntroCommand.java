package net.waqassiddiqi.app.introme.command;

import net.waqassiddiqi.app.introme.Constants;
import net.waqassiddiqi.app.introme.sms.TextManager;

public class SetIntroCommand implements Command {
	private String commandText = "SET INTRO ";
	private String introMessage;
	
	public SetIntroCommand(String introMessage) {
		this.introMessage = introMessage;
	}
	
	public void execute() {
		TextManager.sendSms(Constants.SHORT_CODE, this.commandText + this.introMessage);
	}
}

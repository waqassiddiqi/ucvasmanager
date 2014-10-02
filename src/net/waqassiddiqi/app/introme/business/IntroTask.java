package net.waqassiddiqi.app.introme.business;

import net.waqassiddiqi.app.introme.command.SetIntroCommand;

public class IntroTask implements BusinessTask {
	
	SetIntroCommand cmd;
	
	public IntroTask(String introMessage) {
		cmd = new SetIntroCommand(introMessage);
	}
	
	public void perform() {	
		cmd.execute();		
	}
}

package net.waqassiddiqi.app.introme.business;

import net.waqassiddiqi.app.introme.command.SubscriptionCommand;
import net.waqassiddiqi.app.introme.model.Subscriber;

public class SubscriptionTask implements BusinessTask {
	
	SubscriptionCommand cmd;
	
	public SubscriptionTask(Subscriber subscriber) {
		cmd = new SubscriptionCommand();
	}
	
	public void perform() {	
		cmd.execute();		
	}
}

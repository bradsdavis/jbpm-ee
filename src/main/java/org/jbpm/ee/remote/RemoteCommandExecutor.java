package org.jbpm.ee.remote;

import org.drools.command.Command;

public interface RemoteCommandExecutor {

	public String execute(Command command);
	public Object pollResponse(String correlation);
	
}

package org.jbpm.ee.remote;


public interface RemoteCommandExecutor {

	public String execute(RemoteResponseCommand<?> command);
	public Object pollResponse(String correlation);
	
	
	
}

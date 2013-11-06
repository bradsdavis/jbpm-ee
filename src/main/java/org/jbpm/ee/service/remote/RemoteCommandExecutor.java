package org.jbpm.ee.service.remote;

import javax.ejb.Remote;

import org.jbpm.ee.support.KieReleaseId;

@Remote
public interface RemoteCommandExecutor {

	public String execute(KieReleaseId releaseId, RemoteResponseCommand<?> command);
	public Object pollResponse(String correlation);
	
}

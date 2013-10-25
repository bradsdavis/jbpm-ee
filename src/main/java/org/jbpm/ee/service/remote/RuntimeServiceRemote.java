package org.jbpm.ee.service.remote;

import javax.ejb.Remote;

import org.jbpm.ee.support.KieReleaseId;

@Remote
public interface RuntimeServiceRemote {

	public void setRuntime(KieReleaseId releaseId);
	
}

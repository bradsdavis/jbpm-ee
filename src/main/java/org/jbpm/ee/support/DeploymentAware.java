package org.jbpm.ee.support;

public interface DeploymentAware {
	
	void setDeployment(KieReleaseId releaseId, Long processInstanceId);
}

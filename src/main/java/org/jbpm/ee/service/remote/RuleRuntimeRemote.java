package org.jbpm.ee.service.remote;

import javax.ejb.Remote;

@Remote
public interface RuleRuntimeRemote {

	int fireAllRules(Long processInstanceId);
	
	int fireAllRules(Long processInstanceId, int max);
	
    void insert(Long processInstanceId, Object object);
    
}

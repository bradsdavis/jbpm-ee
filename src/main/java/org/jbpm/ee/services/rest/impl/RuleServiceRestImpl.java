package org.jbpm.ee.services.rest.impl;

import javax.inject.Inject;

import org.jbpm.ee.services.ejb.local.RuleServiceBean;
import org.jbpm.ee.services.rest.RuleServiceRest;

public class RuleServiceRestImpl implements RuleServiceRest {

	@Inject
	private RuleServiceBean ruleRuntime;
	
	@Override
	public int fireAllRules(Long processInstanceId) {
		return ruleRuntime.fireAllRules(processInstanceId);
	}

	@Override
	public int fireAllRules(Long processInstanceId, int max) {
		return ruleRuntime.fireAllRules(processInstanceId, max);
	}

	@Override
	public void insert(Long processInstanceId, Object object) {
		ruleRuntime.insert(processInstanceId, object);
	}

}

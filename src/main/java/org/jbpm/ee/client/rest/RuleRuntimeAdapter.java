package org.jbpm.ee.client.rest;

import org.jbpm.ee.rest.RuleServiceRest;
import org.jbpm.ee.services.RuleService;

public class RuleRuntimeAdapter implements RuleService {

	private final RuleServiceRest ruleService;
	
	public RuleRuntimeAdapter(RuleServiceRest ruleService) {
		this.ruleService = ruleService;
	}
	
	@Override
	public int fireAllRules(Long processInstanceId) {
		return this.ruleService.fireAllRules(processInstanceId);
	}

	@Override
	public int fireAllRules(Long processInstanceId, int max) {
		return this.ruleService.fireAllRules(processInstanceId, max);
	}

	@Override
	public void insert(Long processInstanceId, Object object) {
		this.ruleService.insert(processInstanceId, object);
	}

}

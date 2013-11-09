package org.jbpm.ee.ejb.local;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jbpm.ee.ejb.remote.RuleRuntimeRemote;
import org.jbpm.ee.startup.KnowledgeManagerBean;

@Stateless
@LocalBean
public class RuleRuntimeBean implements RuleRuntimeRemote {

	@EJB
	private KnowledgeManagerBean knowledgeManager;

	@Override
	public int fireAllRules(Long processInstanceId) {
		return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().fireAllRules();
	}

	@Override
	public int fireAllRules(Long processInstanceId, int max) {
		return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().fireAllRules(max);
	}

	@Override
	public void insert(Long processInstanceId, Object object) {
		knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().insert(object);
	}
	
	

	
	
	
}

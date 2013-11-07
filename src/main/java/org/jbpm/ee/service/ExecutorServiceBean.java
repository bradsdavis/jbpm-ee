package org.jbpm.ee.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jbpm.ee.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.command.Command;

@Stateless
@LocalBean
public class ExecutorServiceBean {
	
	@EJB
	private KnowledgeManagerBean knowledgeManager;

	public <T> T executeByReleaseId(KieReleaseId releaseId, Command<T> command) {
		return knowledgeManager.getRuntimeEngine(releaseId).getKieSession().execute(command);
	}

	public <T> T executeByProcessInstanceId(Long processInstanceId, Command<T> command) {
		return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().execute(command);
	}

	public <T> T executeByTaskId(Long taskId, Command<T> command) {
		return knowledgeManager.getRuntimeEngineByTaskId(taskId).getKieSession().execute(command);
	}

	public <T> T executeByWorkItemId(Long workItemId, Command<T> command) {
		return knowledgeManager.getRuntimeEngineByWorkItemId(workItemId).getKieSession().execute(command);
	}

}

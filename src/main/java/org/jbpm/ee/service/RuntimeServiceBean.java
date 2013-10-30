package org.jbpm.ee.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jbpm.ee.exception.SessionException;
import org.jbpm.ee.service.remote.RuntimeServiceRemote;
import org.jbpm.ee.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.AwareStatefulKnowledgeSession;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.task.TaskService;

@Stateful
@LocalBean
@RequestScoped
public class RuntimeServiceBean  implements RuntimeServiceRemote{

	@Inject
	private KnowledgeManagerBean knowledgeManager;
	
	private RuntimeEngine runtimeEngine = null;
	
	@Override
	public void setRuntime(KieReleaseId releaseId) {
		runtimeEngine = knowledgeManager.getRuntimeEngine(releaseId);
	}

	@Override
	public boolean runtimeIsSet() {
		if (runtimeEngine == null) {
			return false;
		}
		return true;
	}

	private void runtimeCheck() {
		if (!runtimeIsSet()) {
			throw new RuntimeException("RuntimeService.setRuntime() must be called first! ");
		}
	}
	
	/**
	 * Sets up a new KnowledgeSession with the Human Task Handler defined.
	 * 
	 * @return
	 * @throws SessionException
	 */
	public KieSession getKnowledgeSession() throws SessionException {
		runtimeCheck();
		return new AwareStatefulKnowledgeSession(runtimeEngine.getKieSession());
	}

	public TaskService getTaskService() throws SessionException {
		runtimeCheck();
		return runtimeEngine.getTaskService();
	}
	
	public WorkItemManager getWorkItemManager() throws SessionException {
		runtimeCheck();
		return runtimeEngine.getKieSession().getWorkItemManager();
	}
}

package org.jbpm.ee.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import org.jbpm.ee.exception.SessionException;
import org.jbpm.ee.service.remote.RuntimeServiceRemote;
import org.jbpm.ee.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.AwareStatefulKnowledgeSession;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;

@Stateful
@LocalBean
public class RuntimeServiceBean  implements RuntimeServiceRemote{

	@EJB
	private KnowledgeManagerBean knowledgeManager;
	
	@EJB
	private TaskServiceBean taskService;
	
	@EJB
	private ProcessRuntimeBean processRuntime;
	
	@EJB 
	private WorkItemManagerBean workItemManager;
	
	private RuntimeEngine runtimeEngine = null;
	
	@Override
	public void setRuntime(KieReleaseId releaseId) {
		runtimeEngine = knowledgeManager.getRuntimeEngine(releaseId);
		processRuntime.setDelegate(runtimeEngine.getKieSession());
		taskService.setDelegate(runtimeEngine.getTaskService());
		workItemManager.setDelegate(runtimeEngine.getKieSession().getWorkItemManager());
	}

	@Override
	public boolean runtimeIsSet() {
		if (runtimeEngine == null) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Sets up a new KnowledgeSession with the Human Task Handler defined.
	 * 
	 * @return
	 * @throws SessionException
	 */
	public KieSession getKnowledgeSession() throws SessionException {
		return new AwareStatefulKnowledgeSession(runtimeEngine.getKieSession());
	}

	
}

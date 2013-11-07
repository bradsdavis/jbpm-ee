package org.jbpm.ee.service;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jbpm.ee.service.remote.ProcessRuntimeRemote;
import org.jbpm.ee.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.KieReleaseId;
import org.jbpm.ee.support.KieReleaseIdXProcessInstanceListener;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItemManager;

@LocalBean
@Stateless
public class ProcessRuntimeBean implements ProcessRuntimeRemote {

	@Inject
	private EntityManager entityManager;
	
	@EJB
	private KnowledgeManagerBean knowledgeManager;

	@Override
	public ProcessInstance startProcess(KieReleaseId releaseId, String processId) {
		KieSession session = knowledgeManager.getRuntimeEngine(releaseId).getKieSession();
		session.addEventListener(new KieReleaseIdXProcessInstanceListener(releaseId, entityManager));
		
		return session.startProcess(processId);
	}

	@Override
	public ProcessInstance startProcess(KieReleaseId releaseId, String processId, Map<String, Object> parameters) {
		KieSession session = knowledgeManager.getRuntimeEngine(releaseId).getKieSession();
		session.addEventListener(new KieReleaseIdXProcessInstanceListener(releaseId, entityManager));
		
		return session.startProcess(processId, parameters);
	}

	@Override
	public ProcessInstance createProcessInstance(KieReleaseId releaseId, String processId, Map<String, Object> parameters) {
		KieSession session = knowledgeManager.getRuntimeEngine(releaseId).getKieSession();
		session.addEventListener(new KieReleaseIdXProcessInstanceListener(releaseId, entityManager));
		
		return session.createProcessInstance(processId, parameters);
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().signalEvent(type, event);
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().getProcessInstance(processInstanceId, true);
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().abortProcessInstance(processInstanceId);
	}

	@Override
	public WorkItemManager getWorkItemManager(long processInstanceId) {
		return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().getWorkItemManager();
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {		
		return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId).getKieSession().startProcessInstance(processInstanceId);
	}

	
}

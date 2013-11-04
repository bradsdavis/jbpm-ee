package org.jbpm.ee.service;

import java.util.Collection;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

import org.jbpm.ee.service.remote.ProcessRuntimeRemote;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.ProcessRuntime;
import org.kie.api.runtime.process.WorkItemManager;

@LocalBean
@Stateful
@ConversationScoped
public class ProcessRuntimeBean implements ProcessRuntimeRemote {

	@Inject
	RuntimeServiceBean runtimeService; 
	
	private ProcessRuntime processRuntimeDelegate;

	private void delegateCheck() {
		if (processRuntimeDelegate == null) {
			processRuntimeDelegate = runtimeService.getKnowledgeSession();
		}
	}
	
	@Override
	public ProcessInstance startProcess(String processId) {
		delegateCheck();
		return processRuntimeDelegate.startProcess(processId);
	}

	@Override
	public ProcessInstance startProcess(String processId, Map<String, Object> parameters) {
		delegateCheck();
		return processRuntimeDelegate.startProcess(processId, parameters);
	}

	@Override
	public ProcessInstance createProcessInstance(String processId, Map<String, Object> parameters) {
		delegateCheck();
		return processRuntimeDelegate.createProcessInstance(processId, parameters);
	}

	@Override
	public void signalEvent(String type, Object event) {
		delegateCheck();
		processRuntimeDelegate.signalEvent(type, event);
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		delegateCheck();
		processRuntimeDelegate.signalEvent(type, event, processInstanceId);
	}

	@Override
	public Collection<ProcessInstance> getProcessInstances() {
		delegateCheck();
		return processRuntimeDelegate.getProcessInstances();
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		delegateCheck();
		return processRuntimeDelegate.getProcessInstance(processInstanceId);
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId, boolean readonly) {
		delegateCheck();
		return processRuntimeDelegate.getProcessInstance(processInstanceId, readonly);
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		delegateCheck();
		processRuntimeDelegate.abortProcessInstance(processInstanceId);
	}

	@Override
	public WorkItemManager getWorkItemManager() {
		delegateCheck();
		return processRuntimeDelegate.getWorkItemManager();
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {		
		delegateCheck();
		return processRuntimeDelegate.startProcessInstance(processInstanceId);
	}

	
}

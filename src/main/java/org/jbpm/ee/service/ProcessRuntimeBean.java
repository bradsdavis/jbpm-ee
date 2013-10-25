package org.jbpm.ee.service;

import java.util.Collection;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import org.jbpm.ee.service.remote.ProcessRuntimeRemote;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.ProcessRuntime;
import org.kie.api.runtime.process.WorkItemManager;

@LocalBean
@Stateful
@RequestScoped
public class ProcessRuntimeBean implements ProcessRuntimeRemote {
	
	private ProcessRuntime processRuntimeDelegate;

	void setDelegate(ProcessRuntime processRuntimeDelegate) {
		this.processRuntimeDelegate = processRuntimeDelegate;
	}
	
	@Override
	public ProcessInstance startProcess(String processId) {
		return processRuntimeDelegate.startProcess(processId);
	}

	@Override
	public ProcessInstance startProcess(String processId, Map<String, Object> parameters) {
		return processRuntimeDelegate.startProcess(processId, parameters);
	}

	@Override
	public ProcessInstance createProcessInstance(String processId, Map<String, Object> parameters) {
		return processRuntimeDelegate.createProcessInstance(processId, parameters);
	}

	@Override
	public void signalEvent(String type, Object event) {
		processRuntimeDelegate.signalEvent(type, event);
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		processRuntimeDelegate.signalEvent(type, event, processInstanceId);
	}

	@Override
	public Collection<ProcessInstance> getProcessInstances() {
		return processRuntimeDelegate.getProcessInstances();
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		return processRuntimeDelegate.getProcessInstance(processInstanceId);
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId, boolean readonly) {
		return processRuntimeDelegate.getProcessInstance(processInstanceId, readonly);
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		processRuntimeDelegate.abortProcessInstance(processInstanceId);
	}

	@Override
	public WorkItemManager getWorkItemManager() {
		return processRuntimeDelegate.getWorkItemManager();
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {		
		return processRuntimeDelegate.startProcessInstance(processInstanceId);
	}

	
}

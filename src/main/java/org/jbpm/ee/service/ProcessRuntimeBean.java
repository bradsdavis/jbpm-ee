package org.jbpm.ee.service;

import java.util.Collection;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jbpm.ee.cdi.ProcessRuntimeConfig;
import org.jbpm.ee.service.remote.ProcessRuntimeRemote;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.ProcessRuntime;
import org.kie.api.runtime.process.WorkItemManager;

@LocalBean
@Stateful
@Remote(ProcessRuntimeRemote.class)
@RequestScoped
public class ProcessRuntimeBean implements ProcessRuntime, ProcessRuntimeRemote {

	@Inject @ProcessRuntimeConfig
	private ProcessRuntime processRuntime;

	@Override
	public ProcessInstance startProcess(String processId) {
		return processRuntime.startProcess(processId);
	}

	@Override
	public ProcessInstance startProcess(String processId, Map<String, Object> parameters) {
		return processRuntime.startProcess(processId, parameters);
	}

	@Override
	public ProcessInstance createProcessInstance(String processId, Map<String, Object> parameters) {
		return processRuntime.createProcessInstance(processId, parameters);
	}

	@Override
	public void signalEvent(String type, Object event) {
		processRuntime.signalEvent(type, event);
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		processRuntime.signalEvent(type, event, processInstanceId);
	}

	@Override
	public Collection<ProcessInstance> getProcessInstances() {
		return processRuntime.getProcessInstances();
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		return processRuntime.getProcessInstance(processInstanceId);
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId, boolean readonly) {
		return processRuntime.getProcessInstance(processInstanceId, readonly);
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		processRuntime.abortProcessInstance(processInstanceId);
	}

	@Override
	public WorkItemManager getWorkItemManager() {
		return processRuntime.getWorkItemManager();
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {
		return processRuntime.startProcessInstance(processInstanceId);
	}
	
	
}

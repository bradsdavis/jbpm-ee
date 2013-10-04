package org.jbpm.ee.service.core;

import java.util.Collection;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.ProcessRuntime;
import org.drools.runtime.process.WorkItemManager;

/**
 * Delegating facade to stateful session, to reduce operations to JBPM operations. 
 * If you plan to operate on the process multiple times, call JBPMSessionBean.getProcessRuntime() and work with the ProcessRuntime returned directly, as this is more expensive.
 * 
 * @author bradsdavis
 *
 */
@Stateless
@LocalBean
public class ProcessRuntimeBean implements ProcessRuntime {

	@EJB
	private JBPMServiceBean jbpmSessionBean;

	@Override
	public ProcessInstance startProcess(String processId) {
		return jbpmSessionBean.getProcessRuntime().startProcess(processId); 
	}

	@Override
	public ProcessInstance startProcess(String processId, Map<String, Object> parameters) {
		return jbpmSessionBean.getProcessRuntime().startProcess(processId, parameters);
	}

	@Override
	public ProcessInstance createProcessInstance(String processId, Map<String, Object> parameters) {
		return jbpmSessionBean.getProcessRuntime().createProcessInstance(processId, parameters);
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {
		return jbpmSessionBean.getProcessRuntime().startProcessInstance(processInstanceId);
	}

	@Override
	public void signalEvent(String type, Object event) {
		jbpmSessionBean.getProcessRuntime().signalEvent(type, event);
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		jbpmSessionBean.getProcessRuntime().signalEvent(type, event, processInstanceId);
	}

	@Override
	public Collection<ProcessInstance> getProcessInstances() {
		return jbpmSessionBean.getProcessRuntime().getProcessInstances();
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		return jbpmSessionBean.getProcessRuntime().getProcessInstance(processInstanceId);
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		jbpmSessionBean.getProcessRuntime().abortProcessInstance(processInstanceId);
	}

	@Override
	public WorkItemManager getWorkItemManager() {
		return jbpmSessionBean.getProcessRuntime().getWorkItemManager();
	}
	
	
}

package org.jbpm.ee.service;

import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.jbpm.ee.service.remote.WorkItemManagerRemote;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

@LocalBean
@Stateful
@SessionScoped
public class WorkItemManagerBean implements WorkItemManager, WorkItemManagerRemote {
	
	@Inject
	RuntimeServiceBean runtimeService; 
	
	private WorkItemManager workItemManagerDelegate;
	
	private void delegateCheck() {
		if (workItemManagerDelegate == null) {
			workItemManagerDelegate = runtimeService.getWorkItemManager();
		}
	}
	
	@Override
	public void completeWorkItem(long id, Map<String, Object> results) {
		delegateCheck();
		this.workItemManagerDelegate.abortWorkItem(id);
	}

	@Override
	public void abortWorkItem(long id) {
		delegateCheck();
		this.workItemManagerDelegate.abortWorkItem(id);
	}

	@Override
	public void registerWorkItemHandler(String workItemName, WorkItemHandler handler) {
		delegateCheck();
		this.workItemManagerDelegate.registerWorkItemHandler(workItemName, handler);
	}

}

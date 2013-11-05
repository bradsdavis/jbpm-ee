package org.jbpm.ee.service;

import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.drools.core.process.instance.WorkItem;
import org.drools.core.process.instance.impl.DefaultWorkItemManager;
import org.jbpm.ee.service.remote.WorkItemManagerRemote;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

@LocalBean
@Stateful
@SessionScoped
public class WorkItemManagerBean implements WorkItemManager, WorkItemManagerRemote {
	
	@Inject
	RuntimeServiceBean runtimeService; 
	
	private DefaultWorkItemManager workItemManagerDelegate;
	
	private void delegateCheck() {
		if (workItemManagerDelegate == null) {
			workItemManagerDelegate = 
					(DefaultWorkItemManager) runtimeService.getWorkItemManager();
		}
	}
	
	@Override
	public void completeWorkItem(long id, Map<String, Object> results) {
		delegateCheck();
		workItemManagerDelegate.abortWorkItem(id);
	}

	@Override
	public void abortWorkItem(long id) {
		delegateCheck();
		workItemManagerDelegate.abortWorkItem(id);
	}

	@Override
	public void registerWorkItemHandler(String workItemName, WorkItemHandler handler) {
		delegateCheck();
		workItemManagerDelegate.registerWorkItemHandler(workItemName, handler);
	}
	
	public WorkItemHandler getWorkItemHandler(String workItemName) {
		delegateCheck();
		return workItemManagerDelegate.getWorkItemHandler(workItemName);
	}
	
	public WorkItem getWorkItem(long id) {
		delegateCheck();
		return workItemManagerDelegate.getWorkItem(id);
	}

}

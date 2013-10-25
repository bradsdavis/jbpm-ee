package org.jbpm.ee.service;

import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import org.jbpm.ee.service.remote.WorkItemManagerRemote;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

@LocalBean
@Remote(WorkItemManagerRemote.class)
@Stateful
@SessionScoped
public class WorkItemManagerBean implements WorkItemManager, WorkItemManagerRemote {
	
	private WorkItemManager workItemManager;
	
	
	@Override
	public void completeWorkItem(long id, Map<String, Object> results) {
		this.workItemManager.abortWorkItem(id);
	}

	@Override
	public void abortWorkItem(long id) {
		this.workItemManager.abortWorkItem(id);
	}

	@Override
	public void registerWorkItemHandler(String workItemName, WorkItemHandler handler) {
		this.workItemManager.registerWorkItemHandler(workItemName, handler);
	}

}

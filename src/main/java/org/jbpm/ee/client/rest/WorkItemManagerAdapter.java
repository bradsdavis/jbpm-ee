package org.jbpm.ee.client.rest;

import java.util.Map;

import org.drools.core.process.instance.WorkItem;
import org.jbpm.ee.rest.WorkItemServiceRest;
import org.jbpm.ee.services.WorkItemService;

public class WorkItemManagerAdapter implements WorkItemService {

	private WorkItemServiceRest restService;
	
	public WorkItemManagerAdapter(WorkItemServiceRest restManager) {
		this.restService = restManager;
	}
	
	@Override
	public void completeWorkItem(long id, Map<String, Object> results) {
		this.restService.completeWorkItem(id, results);
	}

	@Override
	public void abortWorkItem(long id) {
		this.restService.abortWorkItem(id);
	}

	@Override
	public WorkItem getWorkItem(long id) {
		return (WorkItem)this.restService.getWorkItem(id);
	}

}

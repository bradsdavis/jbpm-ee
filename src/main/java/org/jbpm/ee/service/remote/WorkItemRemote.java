package org.jbpm.ee.service.remote;

import java.util.Map;

import org.kie.api.runtime.process.WorkItem;

public interface WorkItemRemote {
	
	public void completeWorkItem(long id, Map<String, Object> results);
	public void abortWorkItem(long id);
	public WorkItem getWorkItem(long id);
}

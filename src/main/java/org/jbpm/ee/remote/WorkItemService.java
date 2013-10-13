package org.jbpm.ee.remote;

import java.util.Map;

import org.drools.process.instance.WorkItem;

public interface WorkItemService {
	
	public void completeWorkItem(long id, Map<String, Object> results);
	public void abortWorkItem(long id);
	public WorkItem getWorkItem(long id);
}

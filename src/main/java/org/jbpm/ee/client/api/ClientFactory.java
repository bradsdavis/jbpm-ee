package org.jbpm.ee.client.api;

import org.jboss.resteasy.client.ProxyFactory;
import org.jbpm.ee.client.rest.ProcessRuntimeAdapter;
import org.jbpm.ee.rest.ProcessServiceRest;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;

public class ClientFactory {

	public ProcessService getProcessRuntime(String url) {
		ProcessServiceRest client = ProxyFactory.create(ProcessServiceRest.class, url);
		return new ProcessRuntimeAdapter(client);
	}

	public TaskService getTaskService(String url) {
		ProcessServiceRest client = ProxyFactory.create(ProcessServiceRest.class, url);
		return new ProcessRuntimeAdapter(client);
	}

		
	
}

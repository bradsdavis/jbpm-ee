package org.jbpm.workitem.camel.consumer;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;

import org.drools.core.process.instance.WorkItemHandler;
import org.jbpm.ee.camel.ProcessXCamel;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;

public abstract class CamelConsumerHandler implements WorkItemHandler, CamelConsumerRouteHandler {

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		//register the consumer.
		this.createCamelRoute(workItem);
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		//remove the camelRoute.
		this.cancelCamelRoute(workItem);
	}
	
	public void persistWorkItemXCamelReference(WorkItem workItem) {
		
		
		
	}

}

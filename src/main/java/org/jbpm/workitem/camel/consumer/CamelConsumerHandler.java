package org.jbpm.workitem.camel.consumer;

import org.drools.core.process.instance.WorkItemHandler;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;

public abstract class CamelConsumerHandler implements WorkItemHandler, CamelConsumerRouteHandler {

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		//register the consumer.
		this.createCamelRoute(workItem.getName(), workItem.getId(), workItem.getProcessInstanceId(), workItem.getParameters());
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		//remove the camelRoute.
		this.cancelCamelRoute(workItem.getName(), workItem.getId(), workItem.getProcessInstanceId(), workItem.getParameters());
	}

}

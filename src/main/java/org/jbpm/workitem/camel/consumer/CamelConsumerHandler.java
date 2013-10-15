package org.jbpm.workitem.camel.consumer;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.drools.core.process.instance.WorkItemHandler;
import org.jbpm.ee.camel.CamelRouteService;
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
		
		try {
			InitialContext initialContext = new InitialContext();
			
			CamelRouteService camelContextService = (CamelRouteService)initialContext.lookup(CamelRouteService.class.getCanonicalName());
			camelContextService.persistCamelRouteToWorkItem(this.getClass(), workItem);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

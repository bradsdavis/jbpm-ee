package org.jbpm.workitem.camel.consumer;

import org.apache.camel.Route;
import org.kie.api.runtime.process.WorkItem;

public interface CamelConsumerRouteHandler {
	public Route createCamelRoute(WorkItem workItem);
	public Route cancelCamelRoute(WorkItem workItem);
}

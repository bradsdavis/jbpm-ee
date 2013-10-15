package org.jbpm.workitem.camel.consumer;

import org.apache.camel.builder.RouteBuilder;
import org.kie.api.runtime.process.WorkItem;

public interface CamelConsumerRouteHandler {
	public RouteBuilder createCamelRoute(WorkItem workItem);
	public void cancelCamelRoute(WorkItem workItem);
}

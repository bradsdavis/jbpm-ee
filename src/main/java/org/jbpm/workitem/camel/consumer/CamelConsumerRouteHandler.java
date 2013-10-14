package org.jbpm.workitem.camel.consumer;

import java.util.Map;

import org.apache.camel.Route;

public interface CamelConsumerRouteHandler {
	public Route createCamelRoute(String workItemName, Long workItemId, Long processInstanceId, Map<String, Object> parameters);
	public Route cancelCamelRoute(String workItemName, Long workItemId, Long processInstanceId, Map<String, Object> parameters);
}

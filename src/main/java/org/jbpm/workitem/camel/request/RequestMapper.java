package org.jbpm.workitem.camel.request;

import org.apache.camel.Processor;
import org.kie.api.runtime.process.WorkItem;

public interface RequestMapper {
	public Processor mapToRequest(WorkItem workItem);
}
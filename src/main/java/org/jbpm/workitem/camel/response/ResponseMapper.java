package org.jbpm.workitem.camel.response;

import org.apache.camel.Exchange;
import org.kie.api.runtime.process.WorkItem;

public interface ResponseMapper {
	public void mapFromResponse(Exchange exchange, WorkItem workItem);
}

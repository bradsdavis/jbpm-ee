package org.jbpm.workitem.camel.response;

import org.apache.camel.Exchange;
import org.kie.api.runtime.process.WorkItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponsePayloadMapper implements ResponseMapper {
	private static final Logger LOG = LoggerFactory.getLogger(ResponsePayloadMapper.class);
	
	private final String responseLocation;
	
	public ResponsePayloadMapper() {
		this.responseLocation = "response";
	}
	
	public ResponsePayloadMapper(String responseLocation) {
		this.responseLocation = responseLocation;
	}
	
	@Override
	public void mapFromResponse(Exchange exchange, WorkItem workItem) {
		if(exchange.hasOut()) {
			Object response = exchange.getOut().getBody();
			workItem.getResults().put(responseLocation, response);
		}
		else {
			LOG.info("No response.");
		}
	}
}

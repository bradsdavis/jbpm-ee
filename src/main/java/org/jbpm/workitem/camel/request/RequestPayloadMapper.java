package org.jbpm.workitem.camel.request;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.kie.api.runtime.process.WorkItem;

public class RequestPayloadMapper implements RequestMapper {
 
	protected final String requestLocation;
	protected final Set<String> headerLocations;
	
	public RequestPayloadMapper() {
		this.requestLocation = "request";
		this.headerLocations = new HashSet<String>();
	}
	
	public RequestPayloadMapper(String requestLocation) {
		this.requestLocation = requestLocation;
		this.headerLocations = new HashSet<String>();
	}
	
	public RequestPayloadMapper(String requestLocation, Set<String> headerLocations) {
		this.requestLocation = requestLocation;
		this.headerLocations = headerLocations;
	}
	
	
	public Processor mapToRequest(WorkItem workItem) {
		Object request = (Object)workItem.getParameters().get(requestLocation);
		workItem.getParameters().remove(requestLocation);
		
		Map<String, Object> requestParams = workItem.getParameters();
		Map<String, Object> headers = new HashMap<String, Object>();
		
		for(String headerLocation : this.headerLocations) {
			//remove from the request params, move to header locations.
			if(requestParams.containsKey(headerLocation)) {
				headers.put(headerLocation, requestParams.get(headerLocation));
				//remove from request parameters
				requestParams.remove(headerLocation);
			}
		}
		
		return new RequestProcessor(request, headers);
	}

	protected class RequestProcessor implements Processor {
		private Object payload;
		private Map<String, Object> headers;
		
		public RequestProcessor(Object payload, Map<String, Object> headers) {
			this.payload = payload;
			this.headers = headers;
		}
		
		@Override
		public void process(Exchange exchange) throws Exception {
			exchange.getIn().setBody(payload);
			exchange.getIn().setHeaders(headers);
		}
	}
}

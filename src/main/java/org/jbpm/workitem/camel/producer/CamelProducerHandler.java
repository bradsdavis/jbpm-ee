package org.jbpm.workitem.camel.producer;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.drools.core.process.instance.WorkItemHandler;
import org.jbpm.workitem.camel.CamelContextService;
import org.jbpm.workitem.camel.request.RequestMapper;
import org.jbpm.workitem.camel.request.RequestPayloadMapper;
import org.jbpm.workitem.camel.response.ResponseMapper;
import org.jbpm.workitem.camel.response.ResponsePayloadMapper;
import org.jbpm.workitem.camel.uri.URIMapper;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CamelProducerHandler implements WorkItemHandler {
	private static final Logger LOG = LoggerFactory.getLogger(CamelProducerHandler.class);

	private final ResponseMapper responseMapper;
	private final RequestMapper requestMapper;
	private final URIMapper uriConverter;
	private final CamelContext context;
	
	public CamelProducerHandler(URIMapper converter) {
		this.context = CamelContextService.getInstance();
		this.uriConverter = converter;
		this.requestMapper = new RequestPayloadMapper();
		this.responseMapper = new ResponsePayloadMapper();
	}
	
	public CamelProducerHandler(URIMapper converter, RequestMapper processorMapper) {
		this.context = CamelContextService.getInstance();
		this.uriConverter = converter;
		this.requestMapper = processorMapper;
		this.responseMapper = new ResponsePayloadMapper();
	}
	
	public CamelProducerHandler(URIMapper converter, RequestMapper processorMapper, ResponseMapper responseMapper) {
		this.context = CamelContextService.getInstance();
		this.uriConverter = converter;
		this.requestMapper = processorMapper;
		this.responseMapper = responseMapper;
	}
	
	private void send(WorkItem workItem) throws URISyntaxException {
		ProducerTemplate template = context.createProducerTemplate();
		
		Processor processor = requestMapper.mapToRequest(workItem);
		URI uri = uriConverter.toURI(workItem.getParameters());
		Endpoint endpoint = context.getEndpoint(uri.toString());
		 
		Exchange exchange = template.send(endpoint, processor);
		this.responseMapper.mapFromResponse(exchange, workItem);
	}
	
	@Override
	public void executeWorkItem(org.kie.api.runtime.process.WorkItem workItem, org.kie.api.runtime.process.WorkItemManager workItemManager) {
		try {
			this.send(workItem);
		}
		catch(Exception e) {
			LOG.error("Exception executing template.", e);
		}
		finally {
			workItemManager.completeWorkItem(workItem.getId(), null);
		}
	}
	
	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
	}
}

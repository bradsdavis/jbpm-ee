package org.jbpm.ee.client.rest;

import java.util.Map;

import org.drools.core.xml.jaxb.util.JaxbMapAdapter;
import org.drools.core.xml.jaxb.util.JaxbStringObjectPair;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.rest.ProcessServiceRest;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.process.ProcessInstance;

/**
 * Adapts the Rest Service JAXB responses to the ProcessRuntime interface. 
 * 
 * @see ProcessService
 * 
 * @author bradsdavis
 *
 */
public class ProcessServiceAdapter implements ProcessService {

	private static final JaxbMapAdapter MAP_ADAPTER = new JaxbMapAdapter();
	
	private final ProcessServiceRest restService;
	
	public ProcessServiceAdapter(ProcessServiceRest restService) {
		this.restService = restService;
	}
	
	@Override
	public ProcessInstance startProcess(KieReleaseId releaseId, String processId) {
		return this.restService.startProcess(releaseId, processId);
	}

	@Override
	public ProcessInstance startProcess(KieReleaseId releaseId, String processId, Map<String, Object> parameters) {
		try {
			return this.restService.startProcess(releaseId, processId, toJax(parameters));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ProcessInstance createProcessInstance(KieReleaseId releaseId, String processId, Map<String, Object> parameters) {
		try {
			return this.restService.createProcessInstance(releaseId, processId, toJax(parameters));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {
		return this.restService.startProcessInstance(processInstanceId);
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		this.restService.signalEvent(type, event, processInstanceId);
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		return this.restService.getProcessInstance(processInstanceId);
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		this.restService.abortProcessInstance(processInstanceId);
	}
	
	private JaxbStringObjectPair[] toJax(Map<String, Object> params) throws Exception {
		return MAP_ADAPTER.marshal(params);
	}

}

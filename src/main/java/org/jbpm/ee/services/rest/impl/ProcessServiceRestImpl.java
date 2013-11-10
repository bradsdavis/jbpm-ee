
package org.jbpm.ee.services.rest.impl;

import java.util.Map;

import javax.inject.Inject;

import org.drools.core.xml.jaxb.util.JaxbMapAdapter;
import org.drools.core.xml.jaxb.util.JaxbStringObjectPair;
import org.jbpm.ee.services.ejb.local.ProcessServiceBean;
import org.jbpm.ee.services.rest.ProcessServiceRest;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.services.client.serialization.jaxb.impl.JaxbProcessInstanceResponse;


public class ProcessServiceRestImpl implements ProcessServiceRest {

	private static final JaxbMapAdapter MAP_ADAPTER = new JaxbMapAdapter();
	
	@Inject
	private ProcessServiceBean processRuntimeService;
	
	@Override
	public JaxbProcessInstanceResponse startProcess(KieReleaseId releaseId, String processId) {
		return new JaxbProcessInstanceResponse(processRuntimeService.startProcess(releaseId, processId));
	}

	@Override
	public JaxbProcessInstanceResponse startProcess(KieReleaseId releaseId, String processId, JaxbStringObjectPair[] params) {
		try {
			Map<String, Object> parameters = toMap(params);
			return new JaxbProcessInstanceResponse(processRuntimeService.startProcess(releaseId, processId, parameters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public JaxbProcessInstanceResponse createProcessInstance(KieReleaseId releaseId, String processId, JaxbStringObjectPair[] params) {
		try {
			Map<String, Object> parameters = toMap(params);
			return new JaxbProcessInstanceResponse(processRuntimeService.createProcessInstance(releaseId, processId, parameters));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	@Override
	public JaxbProcessInstanceResponse startProcessInstance(long processInstanceId) {
		return new JaxbProcessInstanceResponse(processRuntimeService.startProcessInstance(processInstanceId));
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		this.processRuntimeService.signalEvent(type, event, processInstanceId);
	}

	@Override
	public JaxbProcessInstanceResponse getProcessInstance(long processInstanceId) {
		return new JaxbProcessInstanceResponse(processRuntimeService.getProcessInstance(processInstanceId));
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		this.processRuntimeService.abortProcessInstance(processInstanceId);
	}

	
	private Map<String, Object> toMap(JaxbStringObjectPair[] parameters) throws Exception {
		return MAP_ADAPTER.unmarshal(parameters);
	}
	
}

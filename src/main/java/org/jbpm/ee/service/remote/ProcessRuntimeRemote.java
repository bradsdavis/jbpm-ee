package org.jbpm.ee.service.remote;

import java.util.Map;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.process.ProcessInstance;


public interface ProcessRuntimeRemote {
	ProcessInstance startProcess(KieReleaseId releaseId, String processId);
	
	ProcessInstance startProcess(KieReleaseId releaseId, String processId, Map<String, Object> parameters);
	
	ProcessInstance createProcessInstance(KieReleaseId releaseId, String processId, Map<String, Object> parameters);
	
	ProcessInstance startProcessInstance(long processInstanceId);
	
	void signalEvent(String type, Object event, long processInstanceId);
	
	ProcessInstance getProcessInstance(long processInstanceId);
	
	void abortProcessInstance(long processInstanceId);
}

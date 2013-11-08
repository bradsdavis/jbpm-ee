package org.jbpm.ee.service.remote;

import java.util.Map;

import javax.ejb.Remote;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.process.ProcessInstance;

@Path("/process")
@Remote
public interface ProcessRuntimeRemote {

	@POST
    @Path("/{processId}/start")
    ProcessInstance startProcess(KieReleaseId releaseId, String processId);


    @POST
    @Path("/{processId}/start")
    ProcessInstance startProcess(KieReleaseId releaseId, String processId, Map<String, Object> parameters);
    

    @POST
    @Path("/{processId}/")
    @Produces({ "application/xml" })
    ProcessInstance createProcessInstance(KieReleaseId releaseId, String processId, Map<String, Object> parameters);


    @PUT
    @Path("/instance/{processInstanceId}/start")
    ProcessInstance startProcessInstance(long processInstanceId);


    @PUT
    @Path("/instance/{processInstanceId}/event/signal")
    void signalEvent(String type, Object event, long processInstanceId);


    @GET
    @Path("/instance/{processInstanceId}")
    ProcessInstance getProcessInstance(long processInstanceId);


    @PUT
    @Path("/instance/{processInstanceId}/abort")
    void abortProcessInstance(long processInstanceId);

}

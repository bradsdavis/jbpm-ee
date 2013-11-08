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


@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Path("/process")
@Remote
public interface ProcessRuntimeRemote {

	@POST
    @Path("/{processId}/start")
    ProcessInstance startProcess(KieReleaseId releaseId, @PathParam("processId") String processId);


    @POST
    @Path("/{processId}/start")
    ProcessInstance startProcess(KieReleaseId releaseId, @PathParam("processId") String processId, Map<String, Object> parameters);
    

    @POST
    @Path("/{processId}")
    @Produces({ "application/xml" })
    ProcessInstance createProcessInstance(KieReleaseId releaseId, @PathParam("processId") String processId, Map<String, Object> parameters);


    @PUT
    @Path("/instance/{processInstanceId}/start")
    ProcessInstance startProcessInstance(@PathParam("processInstanceId") long processInstanceId);


    @PUT
    @Path("instance/{processInstanceId}/event/signal")
    void signalEvent(String type, Object event, @PathParam("processInstanceId") long processInstanceId);


    @GET
    @Path("instance/{processInstanceId}")
    ProcessInstance getProcessInstance(@PathParam("processInstanceId") long processInstanceId);


    @PUT
    @Path("instance/{processInstanceId}/abort")
    void abortProcessInstance(@PathParam("processInstanceId") long processInstanceId);

}

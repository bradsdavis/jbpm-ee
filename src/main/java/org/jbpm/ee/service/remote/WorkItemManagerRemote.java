package org.jbpm.ee.service.remote;

import java.util.Map;

import javax.ejb.Remote;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.drools.core.process.instance.WorkItem;

@Path("/workitem")
@Remote
public interface WorkItemManagerRemote {

    @PUT
    @Path("{id}/complete")
    void completeWorkItem(long id, Map<String, Object> results);

    @PUT
    @Path("{id}/abort")
    void abortWorkItem(long id);
    
    @GET
    @Path("{id}")
    WorkItem getWorkItem(long id);

}

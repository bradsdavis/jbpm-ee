package org.jbpm.ee.service.remote;

import java.util.Map;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.drools.core.process.instance.WorkItem;

/**
 * 
 * @author bdavis, abaxter
 *
 * Interface for completing, aborting, and getting a WorkItem
 */
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Path("/workitem")
@Remote
public interface WorkItemManagerRemote {

	/**
	 * Completes the specified WorkItem with the given results
	 * 
	 * @param id WorkItem ID
	 * @param results Results of the WorkItem
	 */
    @PUT
    @Path("{id}/complete")
    void completeWorkItem(@PathParam("id") long id, Map<String, Object> results);

    /**
     * Abort the specified WorkItem
     * 
     * @param id WorkItem ID
     */
    @PUT
    @Path("{id}/abort")
    void abortWorkItem(@PathParam("id") long id);
    
    /**
     * Returns the specified WorkItem
     * 
     * @param id WorkItem ID
     * @return The specified WorkItem
     */
    @GET
    @Path("{id}")
    WorkItem getWorkItem(@PathParam("id") long id);

}

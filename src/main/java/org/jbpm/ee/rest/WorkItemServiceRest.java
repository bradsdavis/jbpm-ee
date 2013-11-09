package org.jbpm.ee.rest;

import java.util.Map;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.kie.services.client.serialization.jaxb.impl.JaxbWorkItem;

@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Path("/workitem")
@Remote
public interface WorkItemServiceRest {

    @PUT
    @Path("{id}/complete")
    void completeWorkItem(@PathParam("id") long id, Map<String, Object> results);

    @PUT
    @Path("{id}/abort")
    void abortWorkItem(@PathParam("id") long id);
    
    @GET
    @Path("{id}")
    JaxbWorkItem getWorkItem(@PathParam("id") long id);

}

package org.jbpm.ee.service.remote;

import javax.ejb.Remote;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/process")
@Remote
public interface RuleRuntimeRemote {


    @PUT
    @Path("/instance/{processInstanceId}/rule/fire/all")
	int fireAllRules(Long processInstanceId);
	

    @PUT
    @Path("/instance/{processInstanceId}/rule/fire/max")
	int fireAllRules(Long processInstanceId, int max);
	

    @POST
    @Path("/instance/{processInstanceId}/rule/insert")
    void insert(Long processInstanceId, Object object);
    
}

package org.jbpm.ee.ejb.remote;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * 
 * @author bdavis, abaxter
 * 
 * Interface for BRMS rules runtime
 *
 * For inserting facts and firing rules.
 * 
 * Note: fireAllRules must be run before any rules will be
 * executed, including rules in ruleflow-groups
 *
 */
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Path("/process")
@Remote
public interface RuleRuntimeRemote {


	/**
	 * Set rules to fire for a particular process 
	 * 
	 * @param processInstanceId The process instance's unique identifier
	 * @return The number of rules fired
	 */
    @PUT
    @Path("/instance/{processInstanceId}/rule/fire/all")
	int fireAllRules(Long processInstanceId);
	

    /**
     * Set up to max rules to fire for a particular process 
     * 
     * @param processInstanceId The process instance's unique identifier
     * @param max The maximum number of rules to fire
     * @return The number of rules fired
     */
    @PUT
    @Path("/instance/{processInstanceId}/rule/fire/max")
	int fireAllRules(Long processInstanceId, int max);
	

    /**
     * Insert a fact into the process's rule runtime
     * 
     * @param processInstanceId The process instance's unique identifier
     * @param object The fact to be inserted
     */
    @POST
    @Path("/instance/{processInstanceId}/rule/insert")
    void insert(Long processInstanceId, Object object);
    
}

package org.jbpm.ee.service.remote;

import java.util.Map;

import javax.ws.rs.PathParam;

import org.drools.core.process.instance.WorkItem;

/**
 * 
 * @author bdavis, abaxter
 *
 * Interface for completing, aborting, and getting a WorkItem
 */
public interface WorkItemManagerRemote {

	/**
	 * Completes the specified WorkItem with the given results
	 * 
	 * @param id WorkItem ID
	 * @param results Results of the WorkItem
	 */
    void completeWorkItem(@PathParam("id") long id, Map<String, Object> results);

    /**
     * Abort the specified WorkItem
     * 
     * @param id WorkItem ID
     */
    void abortWorkItem(@PathParam("id") long id);
    
    /**
     * Returns the specified WorkItem
     * 
     * @param id WorkItem ID
     * @return The specified WorkItem
     */
    WorkItem getWorkItem(@PathParam("id") long id);

}

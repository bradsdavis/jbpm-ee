package org.jbpm.ee.ejb.remote;

import javax.ejb.Remote;

import org.jbpm.ee.services.WorkItemService;

/**
 * 
 * @author bdavis, abaxter
 *
 */
@Remote
public interface WorkItemManagerRemote extends WorkItemService {

}

package org.jbpm.ee.ejb.remote;

import javax.ejb.Remote;

import org.jbpm.ee.services.TaskService;

/**
 * 
 * @author bdavis, abaxter
 *
 */
@Remote
public interface TaskServiceRemote extends TaskService {

}

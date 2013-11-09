package org.jbpm.ee.ejb.remote;

import javax.ejb.Remote;

import org.jbpm.ee.services.ProcessService;

/**
 * 
 * @author bdavis, abaxter
 *
 */
@Remote
public interface ProcessRuntimeRemote extends ProcessService {
	
}

package org.jbpm.ee.service.remote;

import javax.ejb.Remote;

import org.kie.api.runtime.CommandExecutor;

@Remote
public interface ExecutorServiceRemote extends CommandExecutor {

}

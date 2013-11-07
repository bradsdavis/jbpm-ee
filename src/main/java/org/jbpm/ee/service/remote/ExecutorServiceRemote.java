package org.jbpm.ee.service.remote;

import javax.ejb.Remote;

import org.jbpm.ee.support.DeploymentAware;
import org.kie.api.command.Command;
import org.kie.api.runtime.CommandExecutor;

@Remote
public interface ExecutorServiceRemote extends CommandExecutor, DeploymentAware {

	public <T> T executeTask(Command<T> command);
}

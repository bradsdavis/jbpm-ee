package org.jbpm.ee.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.jbpm.ee.service.remote.ExecutorServiceRemote;
import org.kie.api.command.Command;
import org.kie.internal.task.api.InternalTaskService;

@Stateful
@LocalBean
@SessionScoped
public class TaskExecutorServiceBean implements ExecutorServiceRemote {

	@Inject
	private RuntimeServiceBean runtimeService;
	
	private InternalTaskService taskServiceDelegate;
	
	private void delegateCheck() {
		if (taskServiceDelegate == null) {
			taskServiceDelegate = (InternalTaskService) runtimeService.getTaskService();
		}
	}
	
	@Override
	public <T> T execute(Command<T> command) {
		delegateCheck();
		return taskServiceDelegate.execute(command);
	}

}

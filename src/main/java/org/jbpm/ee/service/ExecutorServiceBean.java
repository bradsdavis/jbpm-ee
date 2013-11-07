package org.jbpm.ee.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.jbpm.ee.service.remote.ExecutorServiceRemote;
import org.jbpm.ee.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.command.Command;
import org.kie.api.runtime.CommandExecutor;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.internal.task.api.InternalTaskService;

@Stateless
@LocalBean
public class ExecutorServiceBean implements ExecutorServiceRemote {
	
	@EJB
	private KnowledgeManagerBean km;
	
	private RuntimeEngine runtimeEngine;
	
	private CommandExecutor commandExecutorDelegate;
	
	private InternalTaskService taskServiceDelegate;
	
	@Override
	public void setDeployment(KieReleaseId releaseId, Long processInstanceId) {
		runtimeEngine = km.getRuntimeEngine(releaseId, processInstanceId);
		commandExecutorDelegate = runtimeEngine.getKieSession();
		taskServiceDelegate = (InternalTaskService) runtimeEngine.getTaskService();
	}
	
	@Override
	public <T> T execute(Command<T> command) {
		return commandExecutorDelegate.execute(command);
	}

	@Override
	public <T> T executeTask(Command<T> command) {
		return taskServiceDelegate.execute(command);
	}

}

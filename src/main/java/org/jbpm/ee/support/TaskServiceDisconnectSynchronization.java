package org.jbpm.ee.support;

import javax.transaction.Synchronization;

import org.jbpm.task.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to register a call back to the transaction in order to disconnect task service.
 * @author bradsdavis
 *
 */
public class TaskServiceDisconnectSynchronization implements Synchronization {

	private static final Logger LOG = LoggerFactory.getLogger(TaskServiceDisconnectSynchronization.class);
	private final TaskService taskService;
	
	public TaskServiceDisconnectSynchronization(TaskService taskService) {
		this.taskService = taskService;
	}
	
	public void afterCompletion(int val) {
		try {
			taskService.disconnect();
		} catch (Exception e) {
			LOG.error("Exception disconnecting from task service.");
		}
	}

	//do nothing.
	public void beforeCompletion() {};
}

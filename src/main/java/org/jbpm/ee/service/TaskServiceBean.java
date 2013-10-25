package org.jbpm.ee.service;

import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.jbpm.ee.service.remote.TaskServiceRemote;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Attachment;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;

@LocalBean
@Stateful
@Alternative
@SessionScoped
public class TaskServiceBean implements TaskServiceRemote {

	@Inject
	RuntimeServiceBean runtimeService; 
	
	private TaskService taskServiceDelegate;

	private void delegateCheck() {
		if (taskServiceDelegate == null) {
			taskServiceDelegate = runtimeService.getTaskService();
		}
	}
	
	@Override
	public void activate(long taskId, String userId) {
		delegateCheck();
		taskServiceDelegate.activate(taskId, userId);
	}

	@Override
	public void claim(long taskId, String userId) {
		delegateCheck();
		taskServiceDelegate.claim(taskId, userId);

	}

	@Override
	public void claimNextAvailable(String userId, String language) {
		delegateCheck();
		taskServiceDelegate.claimNextAvailable(userId, language);

	}

	@Override
	public void complete(long taskId, String userId, Map<String, Object> data) {
		delegateCheck();
		taskServiceDelegate.complete(taskId, userId, data);

	}

	@Override
	public void delegate(long taskId, String userId, String targetUserId) {
		delegateCheck();
		taskServiceDelegate.delegate(taskId, userId, targetUserId);

	}

	@Override
	public void exit(long taskId, String userId) {
		delegateCheck();
		taskServiceDelegate.exit(taskId, userId);

	}

	@Override
	public void fail(long taskId, String userId, Map<String, Object> faultData) {
		delegateCheck();
		taskServiceDelegate.fail(taskId, userId, faultData);

	}

	@Override
	public void forward(long taskId, String userId, String targetEntityId) {
		delegateCheck();
		taskServiceDelegate.forward(taskId, userId, targetEntityId);

	}

	@Override
	public Task getTaskByWorkItemId(long workItemId) {
		delegateCheck();
		return taskServiceDelegate.getTaskByWorkItemId(workItemId);
	}

	@Override
	public Task getTaskById(long taskId) {
		delegateCheck();
		return taskServiceDelegate.getTaskById(taskId);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsBusinessAdministrator(String userId, String language) {
		delegateCheck();
		return taskServiceDelegate.getTasksAssignedAsBusinessAdministrator(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, String language) {
		delegateCheck();
		return taskServiceDelegate.getTasksAssignedAsPotentialOwner(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(String userId, List<Status> status, String language) {
		delegateCheck();
		return taskServiceDelegate.getTasksAssignedAsPotentialOwnerByStatus(userId, status, language);
	}

	@Override
	public List<TaskSummary> getTasksOwned(String userId, String language) {
		delegateCheck();
		return taskServiceDelegate.getTasksOwned(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksOwnedByStatus(String userId, List<Status> status, String language) {
		delegateCheck();
		return taskServiceDelegate.getTasksOwnedByStatus(userId, status, language);
	}

	@Override
	public List<TaskSummary> getTasksByStatusByProcessInstanceId(long processInstanceId, List<Status> status, String language) {
		delegateCheck();
		return taskServiceDelegate.getTasksByStatusByProcessInstanceId(processInstanceId, status, language);
	}

	@Override
	public List<Long> getTasksByProcessInstanceId(long processInstanceId) {
		delegateCheck();
		return taskServiceDelegate.getTasksByProcessInstanceId(processInstanceId);
	}

	@Override
	public long addTask(Task task, Map<String, Object> params) {
		delegateCheck();
		return taskServiceDelegate.addTask(task, params);
	}

	@Override
	public void release(long taskId, String userId) {
		delegateCheck();
		taskServiceDelegate.release(taskId, userId);

	}

	@Override
	public void resume(long taskId, String userId) {
		delegateCheck();
		taskServiceDelegate.resume(taskId, userId);

	}

	@Override
	public void skip(long taskId, String userId) {
		delegateCheck();
		taskServiceDelegate.skip(taskId, userId);

	}

	@Override
	public void start(long taskId, String userId) {
		delegateCheck();
		taskServiceDelegate.start(taskId, userId);

	}

	@Override
	public void stop(long taskId, String userId) {
		delegateCheck();
		taskServiceDelegate.stop(taskId, userId);

	}

	@Override
	public void suspend(long taskId, String userId) {
		delegateCheck();
		taskServiceDelegate.suspend(taskId, userId);

	}

	@Override
	public void nominate(long taskId, String userId, List<OrganizationalEntity> potentialOwners) {
		delegateCheck();
		taskServiceDelegate.nominate(taskId, userId, potentialOwners);

	}

	@Override
	public Content getContentById(long contentId) {
		delegateCheck();
		return taskServiceDelegate.getContentById(contentId);
	}

	@Override
	public Attachment getAttachmentById(long attachId) {
		delegateCheck();
		return taskServiceDelegate.getAttachmentById(attachId);
	}
	
}

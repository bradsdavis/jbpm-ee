package org.jbpm.ee.service.remote;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import org.kie.api.task.model.Attachment;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;

@Remote
public interface TaskServiceRemote {

	void activate(long taskId, String userId);

	void claim(long taskId, String userId);

	void claimNextAvailable(String userId, String language);

	void complete(long taskId, String userId, Map<String, Object> data);

	void delegate(long taskId, String userId, String targetUserId);

	void exit(long taskId, String userId);

	void fail(long taskId, String userId, Map<String, Object> faultData);

	void forward(long taskId, String userId, String targetEntityId);

	void release(long taskId, String userId);

	void resume(long taskId, String userId);

	void skip(long taskId, String userId);

	void start(long taskId, String userId);

	void stop(long taskId, String userId);

	void suspend(long taskId, String userId);

	void nominate(long taskId, String userId, List<OrganizationalEntity> potentialOwners);

	Task getTaskByWorkItemId(long workItemId);

	Task getTaskById(long taskId);

	List<TaskSummary> getTasksAssignedAsBusinessAdministrator(String userId, String language);

	List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, String language);

	List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(String userId, List<Status> status, String language);

	List<TaskSummary> getTasksOwned(String userId, String language);

	List<TaskSummary> getTasksOwnedByStatus(String userId, List<Status> status, String language);

	List<Long> getTasksByProcessInstanceId(long processInstanceId);

	List<TaskSummary> getTasksByStatusByProcessInstanceId(long processInstanceId, List<Status> status, String language);

	Content getContentById(long contentId);

	Attachment getAttachmentById(long attachId);
    
}

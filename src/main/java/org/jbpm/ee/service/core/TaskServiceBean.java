package org.jbpm.ee.service.core;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jbpm.eventmessaging.EventKey;
import org.jbpm.eventmessaging.EventResponseHandler;
import org.jbpm.task.Attachment;
import org.jbpm.task.Comment;
import org.jbpm.task.Content;
import org.jbpm.task.OrganizationalEntity;
import org.jbpm.task.Status;
import org.jbpm.task.Task;
import org.jbpm.task.TaskService;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.FaultData;

/**
 * Transactional task service.  Delegates to the task service setup by the JBPMSessionBean. 
 * If you are making multiple calls to the task service, call JBPMSessionBean.getTaskService(), as this can be more expensive.
 * @author bradsdavis
 *
 */
@Stateless
@LocalBean
public class TaskServiceBean implements TaskService {

	@EJB
	private JBPMServiceBean jbpmSessionBean;

	@Override
	public void activate(long taskId, String userId) {
		jbpmSessionBean.getTaskService().activate(taskId, userId);
	}

	@Override
	public void addAttachment(long taskId, Attachment attachment, Content content) {
		jbpmSessionBean.getTaskService().addAttachment(taskId, attachment, content);
	}

	@Override
	public void addComment(long taskId, Comment comment) {
		jbpmSessionBean.getTaskService().addComment(taskId, comment);
	}

	@Override
	public void addTask(Task task, ContentData content) {
		jbpmSessionBean.getTaskService().addTask(task, content);

	}

	@Override
	public void claim(long taskId, String userId) {
		jbpmSessionBean.getTaskService().claim(taskId, userId);
	}

	@Override
	public void claim(long taskId, String userId, List<String> groupIds) {
		jbpmSessionBean.getTaskService().claim(taskId, userId, groupIds);
	}

	@Override
	public void claimNextAvailable(String userId, String language) {
		jbpmSessionBean.getTaskService().claimNextAvailable(userId, language);
	}

	@Override
	public void claimNextAvailable(String userId, List<String> groupIds, String language) {
		jbpmSessionBean.getTaskService().claimNextAvailable(userId, groupIds, language);
	}

	@Override
	public void complete(long taskId, String userId, ContentData outputData) {
		jbpmSessionBean.getTaskService().complete(taskId, userId, outputData);
	}

	@Override
	public void completeWithResults(long taskId, String userId, Object results) {
		jbpmSessionBean.getTaskService().completeWithResults(taskId, userId, results);
	}

	@Override
	public boolean connect() {
		return jbpmSessionBean.getTaskService().connect();
	}

	@Override
	public boolean connect(String address, int port) {
		return jbpmSessionBean.getTaskService().connect(address, port);
	}

	@Override
	public void delegate(long taskId, String userId, String targetUserId) {
		jbpmSessionBean.getTaskService().delegate(taskId, userId, targetUserId);
	}

	@Override
	public void deleteAttachment(long taskId, long attachmentId, long contentId) {
		jbpmSessionBean.getTaskService().deleteAttachment(taskId, attachmentId, contentId);
	}

	@Override
	public void deleteComment(long taskId, long commentId) {
		jbpmSessionBean.getTaskService().deleteComment(taskId, commentId);
	}

	@Override
	public void deleteFault(long taskId, String userId) {
		jbpmSessionBean.getTaskService().deleteFault(taskId, userId);
	}

	@Override
	public void deleteOutput(long taskId, String userId) {
		jbpmSessionBean.getTaskService().deleteOutput(taskId, userId);
	}

	@Override
	public void disconnect() throws Exception {
		jbpmSessionBean.getTaskService().disconnect();
	}

	@Override
	public void exit(long taskId, String userId) {
		jbpmSessionBean.getTaskService().exit(taskId, userId);
	}

	@Override
	public void fail(long taskId, String userId, FaultData faultData) {
		jbpmSessionBean.getTaskService().fail(taskId, userId, faultData);
	}

	@Override
	public void forward(long taskId, String userId, String targetEntityId) {
		jbpmSessionBean.getTaskService().forward(taskId, userId, targetEntityId);
	}

	@Override
	public Content getContent(long contentId) {
		return jbpmSessionBean.getTaskService().getContent(contentId);
	}

	@Override
	public List<TaskSummary> getSubTasksAssignedAsPotentialOwner(long parentId, String userId, String language) {
		return jbpmSessionBean.getTaskService().getSubTasksAssignedAsPotentialOwner(parentId, userId, language);
	}

	@Override
	public List<TaskSummary> getSubTasksByParent(long parentId) {
		return jbpmSessionBean.getTaskService().getSubTasksByParent(parentId);
	}

	@Override
	public Task getTask(long taskId) {
		return jbpmSessionBean.getTaskService().getTask(taskId);
	}

	@Override
	public Task getTaskByWorkItemId(long workItemId) {
		return jbpmSessionBean.getTaskService().getTaskByWorkItemId(workItemId);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsBusinessAdministrator(String userId, String language) {
		return jbpmSessionBean.getTaskService().getTasksAssignedAsBusinessAdministrator(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsExcludedOwner(String userId, String language) {
		return jbpmSessionBean.getTaskService().getTasksAssignedAsExcludedOwner(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, String language) {
		return jbpmSessionBean.getTaskService().getTasksAssignedAsPotentialOwner(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, List<String> groupIds, String language) {
		return jbpmSessionBean.getTaskService().getTasksAssignedAsPotentialOwner(userId, groupIds, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, List<String> groupIds, String language, int firstResult, int maxResult) {
		return jbpmSessionBean.getTaskService().getTasksAssignedAsPotentialOwner(userId, groupIds, language, firstResult, maxResult);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsRecipient(String userId, String language) {
		return jbpmSessionBean.getTaskService().getTasksAssignedAsRecipient(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsTaskInitiator(String userId, String language) {
		return jbpmSessionBean.getTaskService().getTasksAssignedAsTaskInitiator(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsTaskStakeholder(String userId, String language) {

		return jbpmSessionBean.getTaskService().getTasksOwned(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksOwned(String userId, String language) {
		return jbpmSessionBean.getTaskService().getTasksOwned(userId, language);
	}

	@Override
	public List<TaskSummary> getTasksOwned(String userId, List<Status> status, String language) {
		return jbpmSessionBean.getTaskService().getTasksOwned(userId, status, language);
	}

	@Override
	public void nominate(long taskId, String userId, List<OrganizationalEntity> potentialOwners) {
		jbpmSessionBean.getTaskService().nominate(taskId, userId, potentialOwners);
	}

	@Override
	public List<?> query(String qlString, Integer size, Integer offset) {
		return jbpmSessionBean.getTaskService().query(qlString, size, offset);
	}

	@Override
	public void register(long taskId, String userId) {
		jbpmSessionBean.getTaskService().register(taskId, userId);
	}

	@Override
	public void registerForEvent(EventKey key, boolean remove, EventResponseHandler responseHandler) {
		jbpmSessionBean.getTaskService().registerForEvent(key, remove, responseHandler);
	}

	@Override
	public void release(long taskId, String userId) {
		jbpmSessionBean.getTaskService().release(taskId, userId);
	}

	@Override
	public void remove(long taskId, String userId) {
		jbpmSessionBean.getTaskService().remove(taskId, userId);
	}

	@Override
	public void resume(long taskId, String userId) {
		jbpmSessionBean.getTaskService().resume(taskId, userId);
	}

	@Override
	public void setDocumentContent(long taskId, Content content) {
		jbpmSessionBean.getTaskService().setDocumentContent(taskId, content);
	}

	@Override
	public void setFault(long taskId, String userId, FaultData fault) {
		jbpmSessionBean.getTaskService().setFault(taskId, userId, fault);
	}

	@Override
	public void setOutput(long taskId, String userId, ContentData outputContentData) {
		jbpmSessionBean.getTaskService().setOutput(taskId, userId, outputContentData);
	}

	@Override
	public void setPriority(long taskId, String userId, int priority) {
		jbpmSessionBean.getTaskService().setPriority(taskId, userId, priority);
	}

	@Override
	public void skip(long taskId, String userId) {
		jbpmSessionBean.getTaskService().skip(taskId, userId);
	}

	@Override
	public void start(long taskId, String userId) {
		jbpmSessionBean.getTaskService().start(taskId, userId);
	}

	@Override
	public void stop(long taskId, String userId) {
		jbpmSessionBean.getTaskService().stop(taskId, userId);
	}

	@Override
	public void suspend(long taskId, String userId) {
		jbpmSessionBean.getTaskService().suspend(taskId, userId);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(String user, List<Status> status, String language) {
		return jbpmSessionBean.getTaskService().getTasksAssignedAsPotentialOwnerByStatus(user, status, language);
	}

	@Override
	public List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatusByGroup(String userId, List<String> groupIds, List<Status> status, String language) {
		return jbpmSessionBean.getTaskService().getTasksAssignedAsPotentialOwnerByStatusByGroup(userId, groupIds, status, language);
	}

}

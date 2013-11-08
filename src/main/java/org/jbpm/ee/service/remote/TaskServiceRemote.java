package org.jbpm.ee.service.remote;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.kie.api.task.model.Attachment;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;

@Path("/task")
@Remote
public interface TaskServiceRemote {

    @PUT
    @Path("{taskId}/activate")
    void activate(@PathParam("taskId") long taskId, String userId);

    @PUT
    @Path("{taskId}/claim")
    void claim(@PathParam("taskId") long taskId, String userId);


    @PUT
    @Path("claim/next")
    void claimNextAvailable(String userId, String language);


    @PUT
    @Path("{taskId}/complete")
    void complete(@PathParam("taskId") long taskId, String userId, Map<String, Object> data);


    @PUT
    @Path("{taskId}/delegate")
    void delegate(@PathParam("taskId") long taskId, String userId, String targetUserId);


    @POST
    @Path("{taskId}/exit")
    void exit(@PathParam("taskId") long taskId, String userId);


    @PUT
    @Path("{taskId}/fail")
    void fail(@PathParam("taskId") long taskId, String userId, Map<String, Object> faultData);


    @PUT
    @Path("{taskId}/forward")
    void forward(@PathParam("taskId") long taskId, String userId, String targetEntityId);

    @PUT
    @Path("{taskId}/release")
    void release(@PathParam("taskId") long taskId, String userId);

    @PUT
    @Path("{taskId}/resume")
    void resume(@PathParam("taskId") long taskId, String userId);

    @PUT
    @Path("{taskId}/skip")
    void skip(@PathParam("taskId") long taskId, String userId);

    @PUT
    @Path("{taskId}/start")
    void start(@PathParam("taskId") long taskId, String userId);

    @PUT
    @Path("{taskId}/stop")
    void stop(@PathParam("taskId") long taskId, String userId);

    @PUT
    @Path("{taskId}/suspend")
    void suspend(@PathParam("taskId") long taskId, String userId);

    @PUT
    @Path("{taskId}/nominate")
    void nominate(@PathParam("taskId") long taskId, String userId, List<OrganizationalEntity> potentialOwners);


    @GET
    @Path("/workitem/{workItemId}/task")
    Task getTaskByWorkItemId(@PathParam("workItemId") long workItemId);


    @GET
    @Path("{taskId}")
    Task getTaskById(@PathParam("taskId") long taskId);


    @GET
    @Path("assigned/{userId}/administrator")
    List<TaskSummary> getTasksAssignedAsBusinessAdministrator(@PathParam("userId") String userId, String language);

    @GET
    @Path("assigned/{userId}/potential/all")
    List<TaskSummary> getTasksAssignedAsPotentialOwner(@PathParam("userId") String userId, String language);


    @GET
    @Path("assigned/{userId}/potential/status")
    List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(@PathParam("userId") String userId, List<Status> status, String language);


    @GET
    @Path("assigned/{userId}/owner/all")
    List<TaskSummary> getTasksOwned(@PathParam("userId") String userId, String language);


    @GET
    @Path("assigned/{userId}/owner/status")
    List<TaskSummary> getTasksOwnedByStatus(@PathParam("userId") String userId, List<Status> status, String language);


    @GET
    @Path("/process/instance/{processInstanceId}/tasks/all")
    List<Long> getTasksByProcessInstanceId(@PathParam("processInstanceId") long processInstanceId);

    
    @GET
    @Path("/process/instance/{processInstanceId}/tasks/status")
    List<TaskSummary> getTasksByStatusByProcessInstanceId(@PathParam("processInstanceId") long processInstanceId, List<Status> status, String language);

    
    @GET
    @Path("/content/{contentId}")
    Content getContentById(@PathParam("contentId") long contentId);

    @GET
    @Path("/attachment/{attachId}")
    Attachment getAttachmentById(@PathParam("attachId") long attachId);
    
}

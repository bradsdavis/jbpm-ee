package org.jbpm.ee.service.remote;

import java.util.Map;

import javax.ejb.Remote;

import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItemManager;

@Remote
public interface ProcessRuntimeRemote {

    ProcessInstance startProcess(KieReleaseId releaseId, String processId);

    ProcessInstance startProcess(KieReleaseId releaseId, String processId, Map<String, Object> parameters);
    
    ProcessInstance createProcessInstance(KieReleaseId releaseId, String processId, Map<String, Object> parameters);

    ProcessInstance startProcessInstance(long processInstanceId);

    void signalEvent(String type, Object event, long processInstanceId);

    ProcessInstance getProcessInstance(long processInstanceId);

    void abortProcessInstance(long processInstanceId);

    WorkItemManager getWorkItemManager(long processInstanceId);
}

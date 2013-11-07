package org.jbpm.ee.service.remote;

import java.util.Map;

import javax.ejb.Remote;

import org.drools.core.process.instance.WorkItem;

@Remote
public interface WorkItemManagerRemote {

    void completeWorkItem(long id, Map<String, Object> results);

    void abortWorkItem(long id);
    
    WorkItem getWorkItem(long id);

}

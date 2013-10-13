package org.jbpm.ee;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.drools.process.instance.WorkItem;
import org.jbpm.ee.remote.WorkItemService;

@Remote(WorkItemService.class)
@Stateless
@LocalBean
public class WorkItemManagerBean implements WorkItemService {

	@EJB
	private JBPMServiceBean jbpmService;
	
	public void completeWorkItem(long id, Map<String, Object> results) {
		jbpmService.getKnowledgeSession().getWorkItemManager().completeWorkItem(id, results);
	}

	public void abortWorkItem(long id) {
		jbpmService.getKnowledgeSession().getWorkItemManager().abortWorkItem(id);
	}

	public WorkItem getWorkItem(long id) {
		return ((org.drools.process.instance.WorkItemManager)jbpmService.getKnowledgeSession()).getWorkItem(id);
	}

}

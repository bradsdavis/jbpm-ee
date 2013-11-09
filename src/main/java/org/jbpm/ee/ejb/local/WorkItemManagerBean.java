package org.jbpm.ee.ejb.local;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.drools.core.common.InternalRuleBase;
import org.drools.core.process.instance.WorkItem;
import org.drools.persistence.info.WorkItemInfo;
import org.jbpm.ee.ejb.remote.WorkItemManagerRemote;
import org.jbpm.ee.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.KieReleaseId;
import org.kie.internal.runtime.manager.RuntimeEnvironment;

@LocalBean
@Stateful
@SessionScoped
public class WorkItemManagerBean implements WorkItemManagerRemote {

	@Inject
	private EntityManager entityManager;
	
	@EJB
	private KnowledgeManagerBean knowledgeManager;

	@Override
	public void completeWorkItem(long id, Map<String, Object> results) {
		knowledgeManager.getRuntimeEngineByWorkItemId(id).getKieSession().getWorkItemManager().completeWorkItem(id, results);
	}

	@Override
	public void abortWorkItem(long id) {
		knowledgeManager.getRuntimeEngineByWorkItemId(id).getKieSession().getWorkItemManager().abortWorkItem(id);
	}

	@Override
	public WorkItem getWorkItem(long id) {
		KieReleaseId releaseId = knowledgeManager.getReleaseIdByWorkItemId(id);
		RuntimeEnvironment environment = knowledgeManager.getRuntimeEnvironment(releaseId);
		
		Query query = entityManager.createQuery("from WorkItemInfo wii where wii.workItemId = :workItemId");
		query.setParameter("workItemId", id);
		WorkItemInfo info = (WorkItemInfo)query.getSingleResult();
		
		return info.getWorkItem(environment.getEnvironment(), (InternalRuleBase)environment.getKieBase());
	}
	

}

package org.jbpm.ee.support;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jbpm.ee.persistence.KieBaseXProcessInstance;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.kie.api.runtime.process.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KieReleaseIdXProcessInstanceListener implements ProcessEventListener {

	private static final Logger LOG = LoggerFactory.getLogger(KieReleaseIdXProcessInstanceListener.class);
	
	private final EntityManager entityManager;
	private final KieReleaseId kieReleaseId;
	
	public KieReleaseIdXProcessInstanceListener(KieReleaseId kri, EntityManager entityManager) {
		this.entityManager = entityManager;
		this.kieReleaseId = kri;
	}
	
	@Override
	public void beforeProcessStarted(ProcessStartedEvent event) {
		ProcessInstance pi = event.getProcessInstance();
		KieBaseXProcessInstance kbx = new KieBaseXProcessInstance();
		
		kbx.setKieProcessInstanceId(pi.getId());
		kbx.setReleaseArtifactId(kieReleaseId.getArtifactId());
		kbx.setReleaseVersion(kieReleaseId.getVersion());
		kbx.setReleaseGroupId(kieReleaseId.getGroupId());
		
		entityManager.persist(kbx);
		
		LOG.info("Created KieBaseXProcessInstance for Process Instance ID: "+kbx.getKieProcessInstanceId());
	}

	@Override
	public void afterProcessStarted(ProcessStartedEvent event) {
		
	}

	@Override
	public void beforeProcessCompleted(ProcessCompletedEvent event) {
		
	}

	@Override
	public void afterProcessCompleted(ProcessCompletedEvent event) {
		Long processInstanceId = event.getProcessInstance().getId();
		
		Query q = entityManager.createQuery("from KieBaseXProcessInstance kb where kb.kieProcessInstanceId=:processInstanceId");
		q.setParameter("processInstanceId", processInstanceId);
		
		KieBaseXProcessInstance xref = (KieBaseXProcessInstance)q.getSingleResult();
		entityManager.remove(xref);
		
		LOG.info("Deleted KieBaseXProcessInstance for Process Instance ID: "+event.getProcessInstance().getId());
	}

	@Override
	public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
		
	}

	@Override
	public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
		
	}

	@Override
	public void beforeNodeLeft(ProcessNodeLeftEvent event) {
		
	}

	@Override
	public void afterNodeLeft(ProcessNodeLeftEvent event) {
		
	}

	@Override
	public void beforeVariableChanged(ProcessVariableChangedEvent event) {
		
	}

	@Override
	public void afterVariableChanged(ProcessVariableChangedEvent event) {
		
	}

}

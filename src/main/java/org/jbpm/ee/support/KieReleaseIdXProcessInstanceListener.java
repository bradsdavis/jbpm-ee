package org.jbpm.ee.support;

import javax.persistence.EntityManager;

import org.jbpm.ee.persistence.KieBaseXProcessInstance;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.kie.api.runtime.process.ProcessInstance;

public class KieReleaseIdXProcessInstanceListener implements ProcessEventListener {

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
	}

	@Override
	public void afterProcessStarted(ProcessStartedEvent event) {
		
	}

	@Override
	public void beforeProcessCompleted(ProcessCompletedEvent event) {
		
	}

	@Override
	public void afterProcessCompleted(ProcessCompletedEvent event) {
		
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

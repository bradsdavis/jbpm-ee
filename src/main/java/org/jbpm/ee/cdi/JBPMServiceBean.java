package org.jbpm.ee.cdi;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionManager;

import org.jbpm.ee.exception.SessionException;
import org.jbpm.ee.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.AwareStatefulKnowledgeSession;
import org.jbpm.ee.support.KieReleaseId;
import org.jbpm.services.task.HumanTaskServiceFactory;
import org.jbpm.shared.services.impl.JbpmJTATransactionManager;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessRuntime;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.task.TaskService;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.persistence.jpa.JPAKnowledgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sets up the task service to use the local task service.
 * Handles the cleanup of the task service and knowledge session.
 *   
 * Creates a stateful knowledge session 
 * @author bradsdavis
 *
 */
@RequestScoped
public class JBPMServiceBean {
	private static final Logger LOG = LoggerFactory.getLogger(JBPMServiceBean.class);
	
	@PersistenceContext(name="org.jbpm.persistence.jpa", unitName="org.jbpm.persistence.jpa")
	EntityManager entityManagerMain;

	@Inject
	KnowledgeManagerBean knowledgeManagerManager;
	
	@Resource(mappedName="java:jboss/TransactionManager")
	private TransactionManager transactionManager;
	
	@Inject
	private KieReleaseId kieResource;

	@Produces @TaskServiceConfig
	public TaskService getTaskService() {
		HumanTaskServiceFactory htsf = new HumanTaskServiceFactory();
		htsf.setEntityManagerFactory(entityManagerMain.getEntityManagerFactory());
		
		JbpmJTATransactionManager transactionManager = new JbpmJTATransactionManager();
		htsf.setJbpmServicesTransactionManager(transactionManager);
		
		return htsf.newTaskService();
	}
	
	/**
	 * Sets up a new KnowledgeSession with the Human Task Handler defined.
	 * 
	 * @return
	 * @throws SessionException
	 */
	
	@Produces @KieSessionConfig
	public KieSession getKnowledgeSession() throws SessionException {
		Environment environment = KnowledgeBaseFactory.newEnvironment();
		environment.set( EnvironmentName.ENTITY_MANAGER_FACTORY, entityManagerMain.getEntityManagerFactory());
		environment.set( EnvironmentName.TRANSACTION_MANAGER, transactionManager);

		//create a session configuration that delegates timers.
		KieSession knowledgeSession = JPAKnowledgeService.newStatefulKnowledgeSession( knowledgeManagerManager.getKieBase(kieResource), null, environment);
		return new AwareStatefulKnowledgeSession(knowledgeSession);
	}

	/**
	 * Cleaner API for working with the jBPM Processes.
	 * @return
	 * @throws SessionException
	 */
	@Produces @ProcessRuntimeConfig
	public ProcessRuntime getProcessRuntime() throws SessionException {
		return this.getKnowledgeSession();
	}
	
	@Produces
	public WorkItemManager getWorkItemService() {
		return this.getProcessRuntime().getWorkItemManager();
	}
	
}


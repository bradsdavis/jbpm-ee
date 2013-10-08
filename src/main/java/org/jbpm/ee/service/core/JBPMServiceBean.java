package org.jbpm.ee.service.core;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionManager;

import org.drools.KnowledgeBaseFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessRuntime;
import org.jbpm.ee.exception.SessionException;
import org.jbpm.ee.service.startup.JBPMTimerService;
import org.jbpm.ee.service.startup.KnowledgeAgentManagerBean;
import org.jbpm.ee.support.AwareStatefulKnowledgeSession;
import org.jbpm.ee.support.EnterpriseSessionConfiguration;
import org.jbpm.ee.support.SLF4JSystemEventLogger;
import org.jbpm.ee.support.SessionDisposalSynchronization;
import org.jbpm.ee.support.TaskServiceDisconnectSynchronization;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.TaskService;
import org.jbpm.task.service.local.LocalTaskService;
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
@Stateless
@LocalBean
public class JBPMServiceBean {
	private static final Logger LOG = LoggerFactory.getLogger(JBPMServiceBean.class);
	
	@PersistenceContext(name="org.jbpm.task", unitName="org.jbpm.task")
	EntityManager entityManagerTask;
	
	@PersistenceContext(name="org.jbpm.persistence.jpa", unitName="org.jbpm.persistence.jpa")
	EntityManager entityManagerMain;

	@EJB
	KnowledgeAgentManagerBean knowledgeAgentManager;
	
	@Resource 
	private TransactionManager transactionManager;

	public TaskService getTaskService() {
		org.jbpm.task.service.TaskService taskService = new org.jbpm.task.service.TaskService(entityManagerTask.getEntityManagerFactory(), new SLF4JSystemEventLogger());
		TaskService localTaskService = new LocalTaskService(taskService);
		
		//register disconnect at commit / fail of session.
		try {
			transactionManager.getTransaction().registerSynchronization(new TaskServiceDisconnectSynchronization(localTaskService));
		} catch (Exception e) {
			throw new SessionException("Exception creating JBPM Session.", e);
		}
		
		return localTaskService;
	}
	
	/**
	 * Sets up a new KnowledgeSession with the Human Task Handler defined.
	 * 
	 * @return
	 * @throws SessionException
	 */
	public StatefulKnowledgeSession getKnowledgeSession() throws SessionException {
		Environment environment = KnowledgeBaseFactory.newEnvironment();
		environment.set( EnvironmentName.ENTITY_MANAGER_FACTORY, entityManagerMain.getEntityManagerFactory());
		environment.set( EnvironmentName.TRANSACTION_MANAGER, transactionManager);

		//create a session configuration that delegates timers.
		KnowledgeSessionConfiguration knowledgeSessionConfiguration = null; //TODO: finish timer service and enable: new EnterpriseSessionConfiguration();
		
		StatefulKnowledgeSession knowledgeSession = JPAKnowledgeService.newStatefulKnowledgeSession( knowledgeAgentManager.getKnowledgeBase(), knowledgeSessionConfiguration, environment);
		
		//setup the task service & handler.
		SyncWSHumanTaskHandler taskHandler = new SyncWSHumanTaskHandler();
		taskHandler.setClient(getTaskService());
		taskHandler.setLocal(true);
		
		//does hardly anything for local.. 
		taskHandler.connect();
	    
	    //setup human task handler.
	    knowledgeSession.getWorkItemManager().registerWorkItemHandler("Human Task", taskHandler);
	    
	    //dispose of the session at the time the current transaction commits. 
		try {
			transactionManager.getTransaction().registerSynchronization(new SessionDisposalSynchronization(knowledgeSession));
		} catch (Exception e) {
			throw new SessionException("Exception creating JBPM Session.", e);
		}
		
		return new AwareStatefulKnowledgeSession(knowledgeSession);
	}

	/**
	 * Cleaner API for working with the jBPM Processes.
	 * @return
	 * @throws SessionException
	 */
	public ProcessRuntime getProcessRuntime() throws SessionException {
		return this.getKnowledgeSession();
	}
	
}


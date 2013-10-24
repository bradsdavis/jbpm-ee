package org.jbpm.ee.service.core;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jbpm.ee.cdi.JBPMServiceBean;
import org.jbpm.ee.service.TaskServiceBean;
import org.jbpm.ee.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.KieReleaseId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class JBPMServiceBeanTest extends BaseJBPMServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(JBPMServiceBeanTest.class);
	
	@EJB
	JBPMServiceBean jbpmServiceBean;
	
	@EJB
	KnowledgeManagerBean knowledgeAgentManagerBean;
	
	@EJB
	KnowledgeManagerBean knowledgeManagerBean;
	
	@EJB
	TaskServiceBean taskServicesBean;
	
	private static final KieReleaseId kri = new KieReleaseId("com.redhat.demo", "testProj", "1.0-SNAPSHOT");
	
	@Test
	@Transactional(value=TransactionMode.DEFAULT)
	public void testJBPMServiceLookup() throws Exception {
		Assert.assertTrue(jbpmServiceBean != null);
		
		LOG.info("Hello world!");
		
		KieSession sns1 = knowledgeManagerBean.getKnowledgeSession(kri);
		KieSession sns2 = knowledgeManagerBean.getKnowledgeSession(kri);
		
		Assert.assertTrue(sns1 != sns2);
		
		LOG.info("IDs: "+sns1.getId()+" , "+sns2.getId());
	}
	
	@Test
	@Transactional(value=TransactionMode.DEFAULT)
	public void testSimpleProcess() throws Exception {
		KieSession sns = knowledgeManagerBean.getKnowledgeSession(kri);
		final String processString = "testProj.testProcess";
		final String variableKey = "processString";
		
		Map<String, Object> processVariables = new HashMap<String, Object>();
		processVariables.put(variableKey, "Initial");
		
		ProcessInstance processInstance = sns.startProcess(processString, processVariables);
		Long processKey = processInstance.getId();
		LOG.info("Process " + processKey + " started.");
		
		LOG.info("process: " + processKey + " - " + processInstance.getState());
	}
}

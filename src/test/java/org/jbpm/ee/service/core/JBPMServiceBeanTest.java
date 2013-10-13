package org.jbpm.ee.service.core;

import javax.ejb.EJB;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jbpm.ee.JBPMServiceBean;
import org.jbpm.ee.startup.KnowledgeAgentManagerBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class JBPMServiceBeanTest extends BaseJBPMServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(JBPMServiceBeanTest.class);
	
	@EJB
	JBPMServiceBean jbpmServiceBean;
	
	@EJB
	KnowledgeAgentManagerBean knowledgeAgentManagerBean;
	
	@Test
	@Transactional(value=TransactionMode.DEFAULT)
	public void testJBPMServiceLookup() throws Exception {
		Assert.assertTrue(jbpmServiceBean != null);
		
		LOG.info("Hello world!");
		
		StatefulKnowledgeSession sns1 = jbpmServiceBean.getKnowledgeSession();
		StatefulKnowledgeSession sns2 = jbpmServiceBean.getKnowledgeSession();
		
		Assert.assertTrue(sns1 != sns2);
		
		LOG.info("IDs: "+sns1.getId()+" , "+sns2.getId());
	}
}

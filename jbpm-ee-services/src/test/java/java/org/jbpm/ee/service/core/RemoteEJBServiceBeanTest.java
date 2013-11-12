package org.jbpm.ee.service.core;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.services.ejb.local.ProcessServiceBean;
import org.jbpm.ee.services.ejb.local.TaskServiceBean;
import org.jbpm.ee.services.ejb.startup.KnowledgeManagerBean;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RemoteEJBServiceBeanTest extends JBPMServiceTest {

	@EJB
	KnowledgeManagerBean knowledgeManagerBean;
	
	@EJB
	ProcessServiceBean processRuntimeBean;
	
	@EJB
	TaskServiceBean taskServicesBean;

	@Override
	public TaskService getTaskService() {
		return taskServicesBean;
	}

	@Override
	public ProcessService getProcessService() {
		return processRuntimeBean;
	}
	
	
}

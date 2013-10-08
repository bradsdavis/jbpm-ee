package org.jbpm.ee.service.core;

import static org.junit.Assert.*;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jbpm.task.query.TaskSummary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TaskServiceBeanTest extends BaseJBPMServiceTest {

	@EJB
	TaskServiceBean taskService;
	
	@Test
	public void testTaskService() throws Exception {
		List<TaskSummary> tasks = taskService.getTasksAssignedAsExcludedOwner("brad", "en-UK");
		
		Assert.assertTrue(tasks.size()==0);
		
	}
}

package org.jbpm.ee.service.core;

import static org.jbpm.ee.test.util.KJarUtil.createKieJar;
import static org.jbpm.ee.test.util.KJarUtil.getPom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.kie.scanner.MavenRepository.getMavenRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jbpm.ee.client.api.RestClientFactory;
import org.jbpm.ee.services.ProcessService;
import org.jbpm.ee.services.TaskService;
import org.jbpm.ee.support.KieReleaseId;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.model.TaskSummary;
import org.kie.scanner.MavenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class RestServiceBeanTest extends BaseJBPMServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(RestServiceBeanTest.class);
	

	public ProcessService getProcessService() {
		return RestClientFactory.getProcessService("http://localhost:8080/test-jbpm-services/rest");
	}
	
	public TaskService getTaskService() {
		return RestClientFactory.getTaskService("http://localhost:8080/test-jbpm-services/rest");
	}
	
	private static final KieReleaseId kri = new KieReleaseId("com.redhat.demo", "testProj", "1.0-SNAPSHOT");
	
	@BeforeClass
    public static void prepare() {
		KieServices ks = KieServices.Factory.get();
        List<String> processes = new ArrayList<String>();
        processes.add("src/test/resources/kjar/testProcess.bpmn2");
        InternalKieModule kjar = createKieJar(ks, kri.toReleaseIdImpl(), processes);
        File pom = new File("target/kmodule", "pom.xml");
        pom.getParentFile().mkdir();
        try {
            FileOutputStream fs = new FileOutputStream(pom);
            fs.write(getPom(kri).getBytes());
            fs.close();
        } catch (Exception e) {
            
        }
        MavenRepository repository = getMavenRepository();
        repository.deployArtifact(kri.toReleaseIdImpl(), kjar, pom);
    }
	
	@Test
	@Transactional(value=TransactionMode.DEFAULT)
	public void testSimpleProcess() throws Exception {
		final String processString = "testProj.testProcess";
		final String variableKey = "processString";
		
		ProcessService processService = getProcessService();
		TaskService taskService = getTaskService();
		
		Map<String, Object> processVariables = new HashMap<String, Object>();
		processVariables.put(variableKey, "Initial");
		
		List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		LOG.info("Tasks: "+tasks);
		int initialCount = tasks.size();
		LOG.info("Tasks: " + initialCount);
		
		LOG.info("KRI: "+kri);
		ProcessInstance processInstance = processService.startProcess(kri, processString, processVariables);
		assertNotNull(processInstance);
        assertEquals(ProcessInstance.STATE_ACTIVE, processInstance.getState());
		
		tasks = taskService.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		for(TaskSummary summary : tasks) {
			LOG.info("Task: " + summary.getId() + ", Status: "+summary.getStatus());
		}
		assertNotNull(tasks);
        assertEquals(initialCount + 1, tasks.size());
        
        //get the last status.
        long taskId = tasks.get(tasks.size()-1).getId();
        
        Map<String,Object> testResults = new HashMap<String,Object>();
        
        taskService.claim(taskId, "abaxter");
        taskService.start(taskId, "abaxter");
        taskService.complete(taskId, "abaxter", testResults);
        
        tasks = taskService.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		assertNotNull(tasks);
        assertEquals(initialCount, tasks.size());
        
        processInstance = processService.getProcessInstance(processInstance.getId());
        assertNull(processInstance);
	}
}

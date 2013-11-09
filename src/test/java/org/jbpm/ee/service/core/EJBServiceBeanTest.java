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

import javax.ejb.EJB;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jbpm.ee.ejb.remote.ProcessRuntimeRemote;
import org.jbpm.ee.ejb.remote.TaskServiceRemote;
import org.jbpm.ee.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.KieReleaseId;
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
public class EJBServiceBeanTest extends BaseJBPMServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(EJBServiceBeanTest.class);
	

	@EJB
	KnowledgeManagerBean knowledgeManagerBean;
	
	@EJB
	ProcessRuntimeRemote processRuntimeBean;
	
	@EJB
	TaskServiceRemote taskServicesBean;
	
	public ProcessRuntimeRemote getProcessRuntimeBean() {
		return processRuntimeBean;
	}
	
	public TaskServiceRemote getTaskServicesBean() {
		return taskServicesBean;
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
		
		Map<String, Object> processVariables = new HashMap<String, Object>();
		processVariables.put(variableKey, "Initial");
		
		List<TaskSummary> tasks = getTaskServicesBean().getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		
		int initialCount = tasks.size();
		LOG.info("Tasks: " + initialCount);
		ProcessInstance processInstance = getProcessRuntimeBean().startProcess(kri, processString, processVariables);
		assertNotNull(processInstance);
        assertEquals(ProcessInstance.STATE_ACTIVE, processInstance.getState());
		
		tasks = getTaskServicesBean().getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		for(TaskSummary summary : tasks) {
			LOG.info("Task: " + summary.getId());
		}
		assertNotNull(tasks);
        assertEquals(initialCount + 1, tasks.size());
        
        long taskId = tasks.get(0).getId();
        
        Map<String,Object> testResults = new HashMap<String,Object>();
        
        getTaskServicesBean().claim(taskId, "abaxter");
        getTaskServicesBean().start(taskId, "abaxter");
        getTaskServicesBean().complete(taskId, "abaxter", testResults);
     // check the state of process instance
        
        tasks = getTaskServicesBean().getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		assertNotNull(tasks);
        assertEquals(0, tasks.size());
        
        processInstance = getProcessRuntimeBean().getProcessInstance(processInstance.getId());
        assertNull(processInstance);
	}
}

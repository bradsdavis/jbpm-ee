package org.jbpm.ee.service.core;

import static org.jbpm.ee.test.util.KJarUtil.createKieJar;
import static org.jbpm.ee.test.util.KJarUtil.getPom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jbpm.ee.service.remote.ProcessRuntimeRemote;
import org.jbpm.ee.service.remote.RuntimeServiceRemote;
import org.jbpm.ee.service.remote.TaskServiceRemote;
import org.jbpm.ee.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.KieReleaseId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.model.TaskSummary;
import org.kie.scanner.MavenRepository;

import static org.kie.scanner.MavenRepository.getMavenRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class JBPMServiceBeanTest extends BaseJBPMServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(JBPMServiceBeanTest.class);
	
	@Inject
	RuntimeServiceRemote runtimeServiceBean;
	
	@EJB
	KnowledgeManagerBean knowledgeManagerBean;
	
	@EJB
	ProcessRuntimeRemote processRuntimeBean;
	
	@EJB
	TaskServiceRemote taskServicesBean;
	
	private static final KieReleaseId kri = new KieReleaseId("com.redhat.demo", "testProj", "1.0-SNAPSHOT");
	
	@Before
    public void prepare() {
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
	
	//@Test
	@Transactional(value=TransactionMode.DEFAULT)
	public void testJBPMServiceLookup() throws Exception {
		Assert.assertTrue(runtimeServiceBean != null);
		
		LOG.info("Hello world!");
		runtimeServiceBean.setRuntime(kri);
		//KieSession sns1 = runtimeServiceBean.getKnowledgeSession();
		//KieSession sns2 = runtimeServiceBean.getKnowledgeSession();
		
		//Assert.assertEquals(sns1, sns2);
		
		//LOG.info("IDs: "+sns1.getId()+" , "+sns2.getId());
	}
	
	@Test
	@Transactional(value=TransactionMode.DEFAULT)
	public void testSimpleProcess() throws Exception {
		runtimeServiceBean.setRuntime(kri);
		final String processString = "testProj.testProcess";
		final String variableKey = "processString";
		
		Map<String, Object> processVariables = new HashMap<String, Object>();
		processVariables.put(variableKey, "Initial");
		
		ProcessInstance processInstance = processRuntimeBean.startProcess(processString, processVariables);
		assertNotNull(processInstance);
        assertEquals(ProcessInstance.STATE_ACTIVE, processInstance.getState());
		
		List<TaskSummary> tasks = taskServicesBean.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		for(TaskSummary summary : tasks) {
			LOG.info("Task: " + summary.getId());
		}
		assertNotNull(tasks);
        assertEquals(1, tasks.size());
        
        long taskId = tasks.get(0).getId();
        
        Map<String,Object> testResults = new HashMap<String,Object>();
        
        taskServicesBean.claim(taskId, "abaxter");
        taskServicesBean.start(taskId, "abaxter");
        taskServicesBean.complete(taskId, "abaxter", testResults);
     // check the state of process instance
        
        tasks = taskServicesBean.getTasksAssignedAsPotentialOwner("abaxter", "en-UK");
		assertNotNull(tasks);
        assertEquals(0, tasks.size());
        
        processInstance = processRuntimeBean.getProcessInstance(processInstance.getId());
        
        assertNull(processInstance);
	}
}

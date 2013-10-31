package org.jbpm.ee.service.core;

import static org.jbpm.ee.test.util.KJarUtil.createKieJar;
import static org.jbpm.ee.test.util.KJarUtil.getPom;
import static org.junit.Assert.assertEquals;
import static org.kie.scanner.MavenRepository.getMavenRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jbpm.ee.service.remote.RemoteFactHandle;
import org.jbpm.ee.service.remote.RuleRuntimeRemote;
import org.jbpm.ee.service.remote.RuntimeServiceRemote;
import org.jbpm.ee.support.KieReleaseId;
import org.jbpm.ee.support.test.TestLoan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.scanner.MavenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class RuleServiceTest extends BaseJBPMServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(RuleServiceTest.class);
	
	@Inject
	RuntimeServiceRemote runtimeServiceBean;
	
	@EJB
	RuleRuntimeRemote ruleRuntimeBean;
	
	private static final KieReleaseId kri = new KieReleaseId("com.redhat.demo", "ruletest", "1.0-SNAPSHOT");
	
	@Before
    public void prepare() {
		KieServices ks = KieServices.Factory.get();
        List<String> processes = new ArrayList<String>();
        processes.add("src/test/resources/kjar/loanCheck.drl");
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
	public void simpleRuleCheck() {
		final Long exp_factCount = 3L;
		final Integer exp_ruleFiredCount = 1;
		runtimeServiceBean.setRuntime(kri);
		
		TestLoan loan1 = new TestLoan();
		TestLoan loan2 = new TestLoan();
		TestLoan loan3 = new TestLoan();
		
		loan1.setIsEligible(false);
		loan1.setLoanAmount(150);
		
		loan2.setIsEligible(false);
		loan2.setLoanAmount(1500);
		
		loan3.setIsEligible(false);
		loan3.setLoanAmount(15000);
		
		RemoteFactHandle loan1_fh =
				ruleRuntimeBean.insert(loan1);
		
		RemoteFactHandle loan2_fh = 
				ruleRuntimeBean.insert(loan2);
		
		RemoteFactHandle loan3_fh = 
				ruleRuntimeBean.insert(loan3);
		
		Integer ruleFiredCount = ruleRuntimeBean.fireAllRules();
		
		assertEquals(exp_ruleFiredCount, ruleFiredCount);
		
		Long factCount = ruleRuntimeBean.getFactCount();
		
		//assertEquals(exp_factCount, factCount);
		
		loan1 = (TestLoan) ruleRuntimeBean.getObject(loan1_fh);
		loan2 = (TestLoan) ruleRuntimeBean.getObject(loan2_fh);
		loan3 = (TestLoan) ruleRuntimeBean.getObject(loan3_fh);
		assertEquals(loan1.getIsEligible(), Boolean.FALSE);
		assertEquals(loan2.getIsEligible(), Boolean.FALSE);
		assertEquals(loan3.getIsEligible(), Boolean.TRUE);
	}
}

package org.jbpm.ee.startup;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.transaction.TransactionManager;

import org.jbpm.ee.config.Configuration;
import org.jbpm.ee.config.ConfigurationFactory;
import org.jbpm.ee.exception.SessionException;
import org.jbpm.ee.support.AwareStatefulKnowledgeSession;
import org.jbpm.ee.support.KieReleaseId;
import org.jbpm.runtime.manager.impl.RuntimeEnvironmentBuilder;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.PropertiesConfiguration;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.runtime.manager.RuntimeEnvironment;
import org.kie.internal.runtime.manager.RuntimeManagerFactory;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.kie.internal.task.api.UserGroupCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starts up and listens for changes to the change set.
 * @author bradsdavis
 *
 */
@Startup
@Singleton(name="KnowledgeAgentManager")
@DependsOn("ResourceManager")
public class KnowledgeManagerBean {
	private static final Logger LOG = LoggerFactory.getLogger(KnowledgeManagerBean.class);
	
	protected Map<KieReleaseId, KieContainer> containers;
	protected Map<KieReleaseId, KieScanner> scanners;
	
	protected Map<KieReleaseId, RuntimeManager> runtimeManagers;
	
	protected KieServices kieServices;
	
	@Inject
	@Configuration(value="scannerPollFrequency")
	protected Long scannerPollFrequency;
	
	@Inject
	protected EntityManagerFactory emf;
	
	@Inject
	protected UserGroupCallback userGroupCallback;
	
	@PostConstruct
	private void setup() {
		 kieServices = KieServices.Factory.get();
		 containers = new HashMap<KieReleaseId, KieContainer>();
		 scanners = new HashMap<KieReleaseId, KieScanner>();
		 
		 runtimeManagers = new HashMap<KieReleaseId, RuntimeManager>();
	}

	@PreDestroy
	private void destroy() {
		LOG.info("Stopping jBPM Resource Change Listeners.");
		
		for(KieScanner scanner : scanners.values()) {
			scanner.stop();
		}
		
		//set for dispose.
		this.scanners = null;
	}

	protected KieContainer getKieContainer(KieReleaseId resourceKey) {
		
		if(!containers.containsKey(resourceKey)) {
			//create a new container.
			KieContainer kieContainer = kieServices.newKieContainer(resourceKey.toReleaseIdImpl());
			KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
			kieScanner.start(scannerPollFrequency);
			
			//register the new container and scanner.
			this.containers.put(resourceKey, kieContainer);
			this.scanners.put(resourceKey, kieScanner);
		}
		return this.containers.get(resourceKey);
	}
	
	protected KieBase getKieBase(KieReleaseId resourceKey) {
		return getKieContainer(resourceKey).getKieBase();
	}	
	
	public RuntimeEngine getRuntimeEngine(KieReleaseId releaseId) throws SessionException {
		if(!runtimeManagers.containsKey(releaseId)) {
			RuntimeEnvironment re = RuntimeEnvironmentBuilder.getDefault()
			.entityManagerFactory(emf)
			.userGroupCallback(userGroupCallback)
			.knowledgeBase(getKieBase(releaseId))
			.persistence(true)
			.get();
			runtimeManagers.put(releaseId, RuntimeManagerFactory.Factory.get().newPerProcessInstanceRuntimeManager(re, releaseId.toString()));
		}
		RuntimeManager rm = runtimeManagers.get(releaseId);
		return rm.getRuntimeEngine(ProcessInstanceIdContext.get());
	}
	
	private static void setDefaultingProperty(String name, String val, PropertiesConfiguration config) {
		if(val == null) return;

		LOG.debug("Setting property: "+name+" to value: "+val);
		config.setProperty(name, val);
	}
	private static void setDefaultingProperty(String name, Boolean val, PropertiesConfiguration config) {
		if(val == null) return;

		String value = Boolean.toString(val);
		LOG.debug("Setting property: "+name+" to value: "+value);
		config.setProperty(name, value);
	}
	private static void setDefaultingProperty(String name, Integer val, PropertiesConfiguration config) {
		if(val == null) return;

		String value = Integer.toString(val);
		LOG.debug("Setting property: "+name+" to value: "+value);
		config.setProperty(name, value);
	}
	
	
	
	
	
}

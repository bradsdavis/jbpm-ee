package org.jbpm.ee.service.startup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.drools.KnowledgeBase;
import org.drools.PropertiesConfiguration;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.ClassPathResource;
import org.jbpm.ee.support.SLF4JSystemEventLogger;
import org.jbpm.ee.support.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starts up and listens for changes to the change set.
 * @author bradsdavis
 *
 */
@Startup
@Singleton(name="KnowledgeAgentManager")
public class KnowledgeAgentManagerBean {
	private static final Logger LOG = LoggerFactory.getLogger(KnowledgeAgentManagerBean.class);
	private static final String DEFAULT_AGENT_NAME = KnowledgeAgentManagerBean.class.getCanonicalName(); 
	private static final String DEFAULT_CHANGESET = "jbpm-changeset.xml"; 
	
	@Resource()
	private String knowledgeAgentName;
	
	private KnowledgeAgent knowledgeAgent;
	
	@Resource(name="drools.agent.scan.resources")
	private Boolean droolsAgentScanResources;
	
	@Resource(name="drools.agent.scan.directories")
	private Boolean droolsAgentScanDirectories;
	
	@Resource(name="drools.agent.new.instance")
	private Boolean droolsAgentNewInstance;
	
	@Resource(name="drools.agent.monitorChangeSetEvents")
	private Boolean droolsAgentMonitorChangeSetEvents;
	
	@Resource(name="drools.agent.useKBaseClassLoaderForCompiling")
	private Boolean droolsAgentUseKBaseClassLoaderForCompiling;
	
	@Resource(name="drools.agent.validationTimeout")
	private Integer droolsAgentValidationTimeout;
	
	@Resource(name="drools.resource.scanner.interval")
	private String droolsResourceScannerInterval;
	
	@Resource(name="drools.changeset.classpath.reference")
	private String changeSetClasspathReference;
	
	
	@PostConstruct
	private void setup() {
		//setup the knowledge agent name.
		knowledgeAgentName = StringUtils.defaultOnEmpty(knowledgeAgentName, DEFAULT_AGENT_NAME);
		changeSetClasspathReference = StringUtils.defaultOnEmpty(changeSetClasspathReference, DEFAULT_CHANGESET);
		
		KnowledgeAgentConfiguration agentConfiguration = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
		setProperty("drools.agent.scan.resources", droolsAgentScanResources, agentConfiguration);
		setProperty("drools.agent.scan.directories", droolsAgentScanDirectories, agentConfiguration);
		setProperty("drools.agent.new.instance", droolsAgentNewInstance, agentConfiguration);
		setProperty("drools.agent.monitorChangeSetEvents", droolsAgentMonitorChangeSetEvents, agentConfiguration);
		setProperty("drools.agent.useKBaseClassLoaderForCompiling", droolsAgentUseKBaseClassLoaderForCompiling, agentConfiguration);
		setProperty("drools.agent.validationTimeout", droolsAgentValidationTimeout, agentConfiguration);

		//setup agent.
		knowledgeAgent = KnowledgeAgentFactory.newKnowledgeAgent(knowledgeAgentName, agentConfiguration);
		
		ResourceChangeScannerConfiguration config = ResourceFactory.getResourceChangeScannerService().newResourceChangeScannerConfiguration();
		setProperty("drools.resource.scanner.interval", droolsResourceScannerInterval, config);
		
		ResourceFactory.getResourceChangeScannerService().configure(config);
		
		LOG.info("Starting jBPM Resource Change Listeners.");
        ResourceFactory.getResourceChangeNotifierService().start();  
        ResourceFactory.getResourceChangeScannerService().start();  
        
        knowledgeAgent.setSystemEventListener(new SLF4JSystemEventLogger());
        knowledgeAgent.applyChangeSet(new ClassPathResource(changeSetClasspathReference));
	}

	@PreDestroy
	private void destroy() {
		LOG.info("Stopping jBPM Resource Change Listeners.");
		ResourceFactory.getResourceChangeNotifierService().stop();
		ResourceFactory.getResourceChangeScannerService().stop();
	}
	
	
	public KnowledgeBase getKnowledgeBase() {
		return knowledgeAgent.getKnowledgeBase();
	}
	

	private static void setProperty(String name, String val, PropertiesConfiguration config) {
		if(val == null) return;

		LOG.debug("Setting property: "+name+" to value: "+val);
		config.setProperty(name, val);
	}
	private static void setProperty(String name, Boolean val, PropertiesConfiguration config) {
		if(val == null) return;

		String value = Boolean.toString(val);
		LOG.debug("Setting property: "+name+" to value: "+value);
		config.setProperty(name, value);
	}
	private static void setProperty(String name, Integer val, PropertiesConfiguration config) {
		if(val == null) return;

		String value = Integer.toString(val);
		LOG.debug("Setting property: "+name+" to value: "+value);
		config.setProperty(name, value);
	}
	
	
	
	
	
}

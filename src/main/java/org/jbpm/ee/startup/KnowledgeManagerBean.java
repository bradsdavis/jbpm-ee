package org.jbpm.ee.startup;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.resource.spi.ConfigProperty;

import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.PropertiesConfiguration;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starts up and listens for changes to the change set.
 * @author bradsdavis
 *
 */
@Startup
@Singleton(name="KnowledgeAgentManager")
public class KnowledgeManagerBean {
	private static final Logger LOG = LoggerFactory.getLogger(KnowledgeManagerBean.class);
	private Map<KieReleaseId, KieContainer> containers;
	private Map<KieReleaseId, KieScanner> scanners;
	
	private KieServices kieServices;
	
	@ConfigProperty
	private Long scannerPollFrequency;
	
	@PostConstruct
	private void setup() {
		 kieServices = KieServices.Factory.get();
		 containers = new HashMap<KieReleaseId, KieContainer>();
		 scanners = new HashMap<KieReleaseId, KieScanner>();
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

	public KieContainer getKieContainer(KieReleaseId resourceKey) {
		
		if(!containers.containsKey(resourceKey)) {
			//create a new container.
			KieContainer kieContainer = kieServices.newKieContainer(resourceKey);
			KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
			
			kieScanner.start(scannerPollFrequency);
			
			//register the new container and scanner.
			this.containers.put(resourceKey, kieContainer);
			this.scanners.put(resourceKey, kieScanner);
		}
		return this.containers.get(resourceKey);
	}
	
	public KieBase getKieBase(KieReleaseId resourceKey) {
		return getKieContainer(resourceKey).getKieBase();
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

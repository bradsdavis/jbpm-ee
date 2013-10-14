package org.jbpm.ee.startup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.camel.Route;
import org.apache.camel.cdi.CdiCamelContext;
import org.jbpm.ee.camel.CamelRouteService;

@Startup
@Singleton
public class CamelBootstrap {

	@Inject
	private CdiCamelContext camelContext;

	@Inject
	private CamelRouteService camelRouteService;
	
	
	@PostConstruct
	public void setup() {

		for(Route route : camelRouteService.loadCamelRoutes()) {
			//TODO: rehydrate the camel routes from the database
		}
		
        // Start Camel Context
        camelContext.start();
	}
	
	@PreDestroy
	public void destroy() {
		camelContext.stop();
	}
	
}

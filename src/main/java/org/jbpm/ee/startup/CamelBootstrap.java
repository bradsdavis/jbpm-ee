package org.jbpm.ee.startup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.camel.builder.RouteBuilder;
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
	public void setup() throws Exception {
		for(RouteBuilder routeBuilder : camelRouteService.loadCamelRoutesFromActiveWorkItems()) {
			camelContext.addRoutes(routeBuilder);
		}
		
        // Start Camel Context
        camelContext.start();
	}
	
	@PreDestroy
	public void destroy() {
		camelContext.stop();
	}
	
}

package org.jbpm.ee.support;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.drools.SessionConfiguration;
import org.drools.time.TimerService;
import org.drools.time.impl.TimerJobFactoryManager;
import org.jbpm.ee.exception.LocatorException;
import org.jbpm.ee.service.startup.JBPMTimerService;

/**
 * Returns the TimerService Singleton, in place of setting it by property.
 * 
 * @author bradsdavis
 */
public class EnterpriseSessionConfiguration extends SessionConfiguration {
	private static final String JBPM_TIMER_SERVICE = JBPMTimerService.class.getCanonicalName(); 
	
	@Override
	public TimerJobFactoryManager getTimerJobFactoryManager() {
		return lookupJBPMTimerService();
	}
	
	@Override
	public TimerService newTimerService() {
		return lookupJBPMTimerService();
	}
	
	protected org.jbpm.ee.service.startup.JBPMTimerService lookupJBPMTimerService() {
		InitialContext ic;
		try {
			ic = new InitialContext();
			return (org.jbpm.ee.service.startup.JBPMTimerService)ic.lookup(JBPM_TIMER_SERVICE);
		} catch (NamingException e) {
			throw new LocatorException("Exception locating "+JBPM_TIMER_SERVICE, e);
		}
	}
	
	
}

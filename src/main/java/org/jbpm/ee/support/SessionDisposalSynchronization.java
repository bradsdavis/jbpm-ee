package org.jbpm.ee.support;

import javax.transaction.Synchronization;

import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to register a call back to the transaction in order to dispose the knowledge session.
 * @author bradsdavis
 * @author duncan doyle
 *
 */
public class SessionDisposalSynchronization implements Synchronization {

	private static final Logger LOG = LoggerFactory.getLogger(SessionDisposalSynchronization.class);
	private final StatefulKnowledgeSession knowledgeSession;
	
	public SessionDisposalSynchronization(StatefulKnowledgeSession sks) {
		this.knowledgeSession = sks;
	}
	
	public void afterCompletion(int val) {
		System.out.println("Disposed: "+knowledgeSession);
	}

	//do nothing.
	public void beforeCompletion() {};
}

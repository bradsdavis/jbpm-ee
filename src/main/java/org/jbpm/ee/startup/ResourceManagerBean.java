package org.jbpm.ee.startup;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.kie.internal.task.api.UserGroupCallback;

@Startup
@Singleton(name="ResourceManager")
public class ResourceManagerBean {

	@PersistenceContext(name="org.jbpm.persistence.jpa", unitName="org.jbpm.persistence.jpa")
	private EntityManagerFactory emf;
	
	@Produces
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	@Produces
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	@Produces
	public UserGroupCallback getUserGroupCallback() {
		return new JBossUserGroupCallbackImpl("classpath:/usergroup.properties");
	}
}

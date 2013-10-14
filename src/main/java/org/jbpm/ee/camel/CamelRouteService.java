package org.jbpm.ee.camel;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.camel.Route;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.jbpm.ee.JBPMServiceBean;

@Stateless
public class CamelRouteService {
	
	@PersistenceContext(name="org.jbpm.persistence.jpa", unitName="org.jbpm.persistence.jpa")
	private EntityManager entityManagerMain;

	@Inject
	private JBPMServiceBean jbpmServiceBean;
	
	public Collection<Route> loadCamelRoutes() {
		Session hibernateSession = (Session)entityManagerMain.getDelegate();
		
		Query query = hibernateSession.createQuery("select wii.name, pii.version, pii.processId wii.from CamelRouteReference as ref, WorkItemInfo as wii, ProcessInstanceInfo pii "
				+ "where pii.processInstanceId = wii.processInstanceId");
		
		//now query to the java object.
		query.setResultTransformer(Transformers.aliasToBean(JBPMToCamelReference.class));
		List<JBPMToCamelReference> routeWorkItemHandlers = (List<JBPMToCamelReference>)query.list();

		return null;
	}
	
	
	
	private class JBPMToCamelReference {
		private String workItemName;
		private String processId;
		
		public String getWorkItemName() {
			return workItemName;
		}
		
		public void setWorkItemName(String workItemName) {
			this.workItemName = workItemName;
		}
		
		public void setProcessId(String processId) {
			this.processId = processId;
		}
		
		public String getProcessId() {
			return processId;
		}
	}
}

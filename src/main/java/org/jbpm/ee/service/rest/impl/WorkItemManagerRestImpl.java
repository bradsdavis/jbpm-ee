package org.jbpm.ee.service.rest.impl;

import java.util.Map;

import javax.inject.Inject;

import org.jbpm.ee.service.WorkItemManagerBean;
import org.jbpm.ee.service.rest.WorkItemManagerRest;
import org.kie.services.client.serialization.jaxb.impl.JaxbWorkItem;

public class WorkItemManagerRestImpl implements WorkItemManagerRest {

	@Inject
	private WorkItemManagerBean workItemManager;
	
	@Override
	public void completeWorkItem(long id, Map<String, Object> results) {
		this.workItemManager.completeWorkItem(id, results);
	}

	@Override
	public void abortWorkItem(long id) {
		this.workItemManager.abortWorkItem(id);
	}

	@Override
	public JaxbWorkItem getWorkItem(long id) {
		return new JaxbWorkItem(this.workItemManager.getWorkItem(id));
	}

}

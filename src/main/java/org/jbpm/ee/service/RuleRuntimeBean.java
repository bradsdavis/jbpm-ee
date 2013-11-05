package org.jbpm.ee.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.jbpm.ee.service.remote.RemoteFactHandle;
import org.jbpm.ee.service.remote.RuleRuntimeRemote;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

@Stateful
@LocalBean
@SessionScoped
public class RuleRuntimeBean implements RuleRuntimeRemote {

	@Inject
	RuntimeServiceBean runtimeService; 
	
	private KieSession ruleRuntimeDelegate;

	// No default bimap, so we do this manually. 
	private Map<RemoteFactHandle, FactHandle> remoteFactHandleMap = 
			new HashMap<RemoteFactHandle, FactHandle>();
	
	private Map<FactHandle, RemoteFactHandle> factHandleMap =
			new HashMap<FactHandle, RemoteFactHandle>();
	
	private void delegateCheck() {
		if (ruleRuntimeDelegate == null) {
			ruleRuntimeDelegate = runtimeService.getKnowledgeSession();
		}
	}
	
	@Override
	public int fireAllRules() {
		delegateCheck();
		return ruleRuntimeDelegate.fireAllRules();
	}

	@Override
	public int fireAllRules(int max) {
		delegateCheck();
		return ruleRuntimeDelegate.fireAllRules(max);
	}

	@Override
	public RemoteFactHandle insert(Object object) {
		delegateCheck();
		FactHandle handle = ruleRuntimeDelegate.insert(object);
		RemoteFactHandle remoteHandle = new RemoteFactHandle();
		// Handle the extremely unlikely case of a UUID collision
		while (remoteFactHandleMap.containsKey(remoteHandle)) {
			remoteHandle = new RemoteFactHandle();
		}
		remoteFactHandleMap.put(remoteHandle, handle);
		factHandleMap.put(handle, remoteHandle);
		return remoteHandle;
	}

	@Override
	public void update(RemoteFactHandle remoteHandle, Object object) {
		delegateCheck();
		FactHandle handle = remoteFactHandleMap.get(remoteHandle);
		ruleRuntimeDelegate.update(handle, object);
	}

	// TODO: Handle cases where Object is known, but we don't have a remote?
	@Override
	public RemoteFactHandle getFactHandle(Object object) {
		delegateCheck();
		FactHandle handle = ruleRuntimeDelegate.getFactHandle(object);
		RemoteFactHandle remoteHandle = factHandleMap.get(handle);
		return remoteHandle;
	}

	@Override
	public Object getObject(RemoteFactHandle remoteHandle) {
		delegateCheck();
		FactHandle handle = remoteFactHandleMap.get(remoteHandle);
		Object obj = ruleRuntimeDelegate.getObject(handle);
		return obj;
	}

	@Override
	public Collection<? extends Object> getObjects() {
		delegateCheck();
		List<Object> objList = new ArrayList<Object>();
		objList.addAll(ruleRuntimeDelegate.getObjects());
		return objList;
	}

	// TODO: Handle cases where Object is known, but we don't have a remote?
	@Override
	public Collection<RemoteFactHandle> getFactHandles() {
		delegateCheck();
		return remoteFactHandleMap.keySet();
	}

	@Override
	public long getFactCount() {
		delegateCheck();
		return ruleRuntimeDelegate.getFactCount();
	}
	
	
}

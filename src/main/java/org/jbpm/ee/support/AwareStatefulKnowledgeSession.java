package org.jbpm.ee.support;

import java.util.Collection;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.command.Command;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.WorkingMemoryEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.Calendars;
import org.kie.api.runtime.Channel;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.Globals;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.rule.Agenda;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.LiveQuery;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.ViewChangedEventListener;
import org.kie.api.time.SessionClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Overrides the dispose() method to safely dispose with the session.
 * @author bradsdavis
 *
 */
public class AwareStatefulKnowledgeSession implements KieSession {

	private static final Logger LOG = LoggerFactory.getLogger(AwareStatefulKnowledgeSession.class);
	private final KieSession delegate;

	public AwareStatefulKnowledgeSession(KieSession sks) {
		this.delegate = sks;
	}

	@Override
	public int getId() {
		return delegate.getId();
	}

	@Override
	public void dispose() {
		 LOG.warn("Dispose not neccessary.  This is handled by the session listener.");
	}

	@Override
	public void destroy() {
		 delegate.destroy();
	}

	@Override
	public int fireAllRules() {
		return  delegate.fireAllRules();
	}

	@Override
	public int fireAllRules(int max) {
		 return delegate.fireAllRules(max);
	}

	@Override
	public int fireAllRules(AgendaFilter agendaFilter) {
		 return delegate.fireAllRules(agendaFilter);
		
	}

	@Override
	public int fireAllRules(AgendaFilter agendaFilter, int max) {
		 return delegate.fireAllRules(agendaFilter, max);
		
	}

	@Override
	public void fireUntilHalt() {
		 delegate.fireUntilHalt();
		
	}

	@Override
	public void fireUntilHalt(AgendaFilter agendaFilter) {
		 delegate.fireUntilHalt(agendaFilter);
		
	}

	@Override
	public <T> T execute(Command<T> command) {
		return delegate.execute(command);
	}

	@Override
	public <T extends SessionClock> T getSessionClock() {
		return delegate.getSessionClock();
	}

	@Override
	public void setGlobal(String identifier, Object value) {
		delegate.setGlobal(identifier, value);
	}

	@Override
	public Object getGlobal(String identifier) {
		 return delegate.getGlobal(identifier);
	}

	@Override
	public Globals getGlobals() {
		 return delegate.getGlobals();
	}

	@Override
	public Calendars getCalendars() {
		return delegate.getCalendars();
	}

	@Override
	public Environment getEnvironment() {
		return delegate.getEnvironment();
	}

	@Override
	public void registerChannel(String name, Channel channel) {
		delegate.registerChannel(name, channel);
	}

	@Override
	public void unregisterChannel(String name) {
		delegate.unregisterChannel(name);
	}

	@Override
	public Map<String, Channel> getChannels() {
		return delegate.getChannels();
	}

	@Override
	public KieSessionConfiguration getSessionConfiguration() {
		return delegate.getSessionConfiguration();
	}

	@Override
	public void halt() {
		 delegate.halt();
		
	}

	@Override
	public Agenda getAgenda() {
		return delegate.getAgenda();
	}

	@Override
	public EntryPoint getEntryPoint(String name) {
		return delegate.getEntryPoint(name);
	}

	@Override
	public Collection<? extends EntryPoint> getEntryPoints() {
		return delegate.getEntryPoints();
	}

	@Override
	public QueryResults getQueryResults(String query, Object... arguments) {
		return delegate.getQueryResults(query, arguments);
	}

	@Override
	public LiveQuery openLiveQuery(String query, Object[] arguments, ViewChangedEventListener listener) {
		return delegate.openLiveQuery(query, arguments, listener);
	}

	@Override
	public String getEntryPointId() {
		return delegate.getEntryPointId();
	}

	@Override
	public FactHandle insert(Object object) {
		return delegate.insert(object);
	}

	@Override
	public void retract(FactHandle handle) {
		delegate.retract(handle);
	}

	@Override
	public void delete(FactHandle handle) {
		delegate.delete(handle);
	}

	@Override
	public void update(FactHandle handle, Object object) {
		delegate.update(handle, object);
	}

	@Override
	public FactHandle getFactHandle(Object object) {
		return delegate.getFactHandle(object);
	}

	@Override
	public Object getObject(FactHandle factHandle) {
		return delegate.getObject(factHandle);
	}

	@Override
	public Collection<? extends Object> getObjects() {
		return delegate.getObjects();
	}

	@Override
	public Collection<? extends Object> getObjects(ObjectFilter filter) {
		return delegate.getObjects(filter);
	}

	@Override
	public <T extends FactHandle> Collection<T> getFactHandles() {
		return delegate.getFactHandles();
	}

	@Override
	public <T extends FactHandle> Collection<T> getFactHandles(ObjectFilter filter) {
		return delegate.getFactHandles(filter);
	}

	@Override
	public long getFactCount() {
		return delegate.getFactCount();
	}

	@Override
	public ProcessInstance startProcess(String processId) {
		return delegate.startProcess(processId);
	}

	@Override
	public ProcessInstance startProcess(String processId, Map<String, Object> parameters) {
		return delegate.startProcess(processId, parameters);
	}

	@Override
	public ProcessInstance createProcessInstance(String processId, Map<String, Object> parameters) {
		return delegate.createProcessInstance(processId, parameters);
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {
		return delegate.startProcessInstance(processInstanceId);
	}

	@Override
	public void signalEvent(String type, Object event) {
		delegate.signalEvent(type, event);
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		delegate.signalEvent(type, event, processInstanceId);
	}

	@Override
	public Collection<ProcessInstance> getProcessInstances() {
		return delegate.getProcessInstances();
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		 return delegate.getProcessInstance(processInstanceId);
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId, boolean readonly) {
		 return delegate.getProcessInstance(processInstanceId, readonly);
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		 delegate.abortProcessInstance(processInstanceId);
	}

	@Override
	public WorkItemManager getWorkItemManager() {
		 return delegate.getWorkItemManager();
	}

	@Override
	public void addEventListener(WorkingMemoryEventListener listener) {
		 delegate.addEventListener(listener);
	}

	@Override
	public void removeEventListener(WorkingMemoryEventListener listener) {
		 delegate.removeEventListener(listener);
	}

	@Override
	public Collection<WorkingMemoryEventListener> getWorkingMemoryEventListeners() {
		return delegate.getWorkingMemoryEventListeners();
	}

	@Override
	public void addEventListener(AgendaEventListener listener) {
		 delegate.addEventListener(listener);
	}

	@Override
	public void removeEventListener(AgendaEventListener listener) {
		 delegate.removeEventListener(listener);
	}

	@Override
	public Collection<AgendaEventListener> getAgendaEventListeners() {
		return delegate.getAgendaEventListeners();
	}

	@Override
	public void addEventListener(ProcessEventListener listener) {
		 delegate.addEventListener(listener);
	}

	@Override
	public void removeEventListener(ProcessEventListener listener) {
		 delegate.removeEventListener(listener);
	}

	@Override
	public Collection<ProcessEventListener> getProcessEventListeners() {
		return delegate.getProcessEventListeners();
	}

	@Override
	public KieBase getKieBase() {
		return delegate.getKieBase();
	}

	@Override
	public KieRuntimeLogger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}
}

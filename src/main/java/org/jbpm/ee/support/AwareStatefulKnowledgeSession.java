package org.jbpm.ee.support;

import java.util.Collection;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.command.Command;
import org.drools.event.process.ProcessEventListener;
import org.drools.event.rule.AgendaEventListener;
import org.drools.event.rule.WorkingMemoryEventListener;
import org.drools.runtime.Calendars;
import org.drools.runtime.Channel;
import org.drools.runtime.Environment;
import org.drools.runtime.ExitPoint;
import org.drools.runtime.Globals;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.ObjectFilter;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkItemManager;
import org.drools.runtime.rule.Agenda;
import org.drools.runtime.rule.AgendaFilter;
import org.drools.runtime.rule.FactHandle;
import org.drools.runtime.rule.LiveQuery;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.ViewChangedEventListener;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.drools.time.SessionClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Overrides the dispose() method to safely dispose with the session.
 * @author bradsdavis
 *
 */
public class AwareStatefulKnowledgeSession implements StatefulKnowledgeSession {

	private static final Logger LOG = LoggerFactory.getLogger(AwareStatefulKnowledgeSession.class);
	private final StatefulKnowledgeSession delegate;

	public AwareStatefulKnowledgeSession(StatefulKnowledgeSession session) {
		this.delegate = session;
	}

	@Override
	public int fireAllRules() {
		return delegate.fireAllRules();
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
	public KnowledgeBase getKnowledgeBase() {
		return delegate.getKnowledgeBase();
	}

	@Override
	public void registerExitPoint(String name, ExitPoint exitPoint) {
		delegate.registerExitPoint(name, exitPoint);
	}

	@Override
	public void unregisterExitPoint(String name) {
		delegate.unregisterExitPoint(name);
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
	public KnowledgeSessionConfiguration getSessionConfiguration() {
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
	public WorkingMemoryEntryPoint getWorkingMemoryEntryPoint(String name) {
		return delegate.getWorkingMemoryEntryPoint(name);
	}

	@Override
	public Collection<? extends WorkingMemoryEntryPoint> getWorkingMemoryEntryPoints() {
		return delegate.getWorkingMemoryEntryPoints();
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
	public Collection<Object> getObjects() {
		return delegate.getObjects();
	}

	@Override
	public Collection<Object> getObjects(ObjectFilter filter) {
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
		delegate.removeEventListener(listener);
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
	public int getId() {
		return delegate.getId();
	}

	@Override
	public void dispose() {
		LOG.warn("Dispose is handled by the EJB container when leveraging the jBPM EE libraries.  Dispose not required in this context.");
	}

}

package org.jbpm.ee.remote.command;

import java.util.Map;

import org.drools.command.Context;
import org.jbpm.ee.remote.RemoteResponseCommand;

public class StartProcessCommand implements RemoteResponseCommand<Long> {

	private final org.drools.command.runtime.process.StartProcessCommand delegate;

    public StartProcessCommand(String processId) {
        delegate = new org.drools.command.runtime.process.StartProcessCommand(processId);
    }

    public StartProcessCommand(String processId, String outIdentifier) {
    	delegate = new org.drools.command.runtime.process.StartProcessCommand(processId, outIdentifier);
    }

    public StartProcessCommand(String processId, Map<String, Object> parameters) {
    	delegate = new org.drools.command.runtime.process.StartProcessCommand(processId, parameters);
    }

    public StartProcessCommand(String processId, Map<String, Object> parameters, String outIdentifier) {
    	delegate = new org.drools.command.runtime.process.StartProcessCommand(processId, parameters, outIdentifier);
    }

	@Override
	public Long execute(Context context) {
		return delegate.execute(context).getId();
	}

}

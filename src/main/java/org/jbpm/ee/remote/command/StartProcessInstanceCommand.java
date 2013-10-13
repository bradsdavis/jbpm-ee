package org.jbpm.ee.remote.command;

import org.drools.command.Context;
import org.jbpm.ee.remote.RemoteVoidCommand;

public class StartProcessInstanceCommand implements RemoteVoidCommand {

	private final org.drools.command.runtime.process.StartProcessInstanceCommand delegate;

    public StartProcessInstanceCommand(Long processInstanceId) {
        delegate = new org.drools.command.runtime.process.StartProcessInstanceCommand(processInstanceId);
    }

	@Override
	public Void execute(Context context) {
		delegate.execute(context);
		
		return null;
	}

}

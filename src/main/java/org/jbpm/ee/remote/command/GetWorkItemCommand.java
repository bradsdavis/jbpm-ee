package org.jbpm.ee.remote.command;

import org.drools.command.Context;
import org.drools.process.instance.impl.WorkItemImpl;
import org.jbpm.ee.remote.RemoteResponseCommand;

public class GetWorkItemCommand implements RemoteResponseCommand<WorkItemImpl> {

	private final org.drools.command.runtime.process.GetWorkItemCommand delegate;
	
    public GetWorkItemCommand(long workItemId) {
        delegate = new org.drools.command.runtime.process.GetWorkItemCommand(workItemId);
    }
	
	@Override
	public WorkItemImpl execute(Context context) {
		return (WorkItemImpl)delegate.execute(context);
	}

}

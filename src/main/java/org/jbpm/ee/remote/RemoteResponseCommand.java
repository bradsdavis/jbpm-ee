package org.jbpm.ee.remote;

import java.io.Serializable;

import org.drools.core.command.impl.GenericCommand;

public interface RemoteResponseCommand<T extends Serializable> extends GenericCommand<T> {

}

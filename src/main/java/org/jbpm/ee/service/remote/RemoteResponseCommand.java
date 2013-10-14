package org.jbpm.ee.service.remote;

import java.io.Serializable;

import org.drools.core.command.impl.GenericCommand;

public interface RemoteResponseCommand<T extends Serializable> extends GenericCommand<T> {

}

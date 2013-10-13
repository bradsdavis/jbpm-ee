package org.jbpm.ee;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.drools.command.Command;
import org.drools.runtime.CommandExecutor;

@LocalBean
@Stateless
public class CommandExecutorBean implements CommandExecutor {

	@EJB
	private JBPMServiceBean jbpmService;
	
	@Override
	public <T> T execute(Command<T> command) {
		return jbpmService.getKnowledgeSession().execute(command);
	}

}

package org.jbpm.ee.jms;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.drools.core.command.impl.GenericCommand;
import org.jbpm.ee.exception.CommandException;
import org.jbpm.ee.startup.KnowledgeManagerBean;
import org.jbpm.ee.support.KieReleaseId;
import org.jbpm.services.task.commands.TaskCommand;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(name = "CommandRequestMDB", activationConfig = {
		 @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		 @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/JBPMCommandRequestQueue")
})
public class CommandExecutorMDB implements MessageListener {

	private static final Logger LOG = LoggerFactory.getLogger(CommandExecutorMDB.class);

	@Resource(mappedName = "java:/JmsXA")
	private ConnectionFactory connectionFactory;

	private Connection connection;
	private Session session;
	
	@EJB
	private KnowledgeManagerBean knowledgeManager;
	
    @PostConstruct
    public void init() throws JMSException {
        connection = connectionFactory.createConnection();
        session = connection.createSession(); 
    }
    
    
    public RuntimeEngine getRuntimeEngine(Message request) throws JMSException {
		String groupId = request.getStringProperty("groupId");
		String artifactId = request.getStringProperty("artifactId");
		String version = request.getStringProperty("version");
		
		return knowledgeManager.getRuntimeEngine(new KieReleaseId(groupId, artifactId, version));
    }
    
    public RuntimeEngine getRuntimeEngine(GenericCommand<?> command) {
    	RuntimeEngine runtimeEngine = null;
		
		if(TaskCommand.class.isAssignableFrom(command.getClass())) {
			TaskCommand<?> taskCommand = (TaskCommand<?>)command;
			return knowledgeManager.getRuntimeEngineByTaskId(taskCommand.getTaskId());
		}
		
		//else, try it by process instance id.
		Long processInstanceId = getProcessInstanceIdFromCommand(command);
		if(processInstanceId!=null) {
			return knowledgeManager.getRuntimeEngineByProcessId(processInstanceId);
		}
		
		//else, try it by work item id.
		Long workItemId = getWorkItemIdFromCommand(command);
		if(workItemId != null) {
			return knowledgeManager.getRuntimeEngineByWorkItemId(workItemId);
		}
		
		return null;
    }
	
	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;

		try {
			//check the command and lookup the kie session.
			GenericCommand<?> command = (GenericCommand<?>)objectMessage.getObject();
			
			RuntimeEngine engine = getRuntimeEngine(command);

			//build it from the message properties...
			if(engine == null) {
				engine = getRuntimeEngine(message);
			}
			
			// this should be the command object.
			Object commandResponse = engine.getKieSession().execute(command);

			if (!(commandResponse instanceof Void)) {
				// see if there is a correlation and reply to.

				String correlation = message.getJMSCorrelationID();
				Destination responseQueue = message.getJMSReplyTo();

				if (responseQueue != null && correlation != null) {
					
					if(!Serializable.class.isAssignableFrom(commandResponse.getClass())) {
						throw new CommandException("Unable to send response for command, since it is not serializable.");
					}
					
			        MessageProducer producer = session.createProducer(responseQueue);
			        
					ObjectMessage responseMessage = session.createObjectMessage();
					responseMessage.setObject((Serializable)commandResponse);
					producer.send(responseMessage);
				} 
				else {
					LOG.warn("Response from Command Object, but no ReplyTo and Coorelation: " + ReflectionToStringBuilder.toString(commandResponse));
				}
			}
		} catch (JMSException e) {
			throw new CommandException("Exception processing command via JMS.", e);
		}

	}


	private Long getWorkItemIdFromCommand(final GenericCommand<?> command) {
		return getLongFromCommand("getWorkItemId", command);
	}
	
	private Long getProcessInstanceIdFromCommand(final GenericCommand<?> command) {
		return getLongFromCommand("getProcessInstanceId", command);
	}
	
	private Long getLongFromCommand(final String methodName, final GenericCommand<?> command) {
		Method[] methods = command.getClass().getDeclaredMethods();
		
		if(methods != null) {
			for(Method method : methods) {
				if(method.getName().equals(methodName)) {
					Object result;
					try {
						result = method.invoke(command, null);
						return (Long)result;
					} catch (Exception e) {
						LOG.error("Exception invoking method: "+methodName, e);
					}
				}
			}
		}
		
		return null;
		
	}
	
}

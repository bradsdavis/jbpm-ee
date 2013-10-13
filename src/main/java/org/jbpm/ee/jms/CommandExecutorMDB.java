package org.jbpm.ee.jms;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.drools.command.Command;
import org.jbpm.ee.CommandExecutorBean;
import org.jbpm.ee.exception.CommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven
public class CommandExecutorMDB implements MessageListener {

	private static final Logger LOG = LoggerFactory.getLogger(CommandExecutorMDB.class);

	@Resource
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "jms/JBPMCommandRequestQueue")
	private Queue queue;
	
	private Connection connection;
	private Session session;

	@EJB
	private CommandExecutorBean commandService;


    @PostConstruct
    public void init() throws JMSException {
        connection = connectionFactory.createConnection();
        session = connection.createSession(); 
    }
	
	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;

		try {
			// this should be the command object.
			Object commandResponse = commandService.execute((Command) objectMessage.getObject());

			if (!(commandResponse instanceof Void)) {
				// see if there is a correlation and reply to.

				String correlation = message.getJMSCorrelationID();
				Destination responseQueue = message.getJMSReplyTo();

				if (responseQueue != null && correlation != null) {
					
					if(!Serializable.class.isAssignableFrom(commandResponse.getClass())) {
						throw new CommandException("Unable to send response for command, since it is not serializable.");
					}
					
			        MessageProducer producer = session.createProducer(queue);
			        
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

}

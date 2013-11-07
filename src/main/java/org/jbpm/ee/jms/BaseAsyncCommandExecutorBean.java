package org.jbpm.ee.jms;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.xml.bind.JAXBException;

import org.jbpm.ee.support.KieReleaseId;
import org.kie.api.command.Command;
import org.kie.services.client.serialization.jaxb.JaxbCommandsRequest;
import org.kie.services.client.serialization.jaxb.JaxbSerializationProvider;
import org.mvel2.sh.CommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Stateless
@LocalBean
public class BaseAsyncCommandExecutorBean {

	private static final Logger LOG = LoggerFactory.getLogger(BaseAsyncCommandExecutorBean.class);
	
	@Resource(mappedName = "java:/JmsXA")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/jms/BaseCommandRequestQueue")
	private Queue requestQueue;
	
	@Resource(mappedName = "java:/jms/JBPMCommandResponseQueue")
	private Topic responseQueue;
	
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	
    @PostConstruct
    public void init()  {
        try {
			connection = connectionFactory.createConnection();
	        session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
	        producer = session.createProducer(requestQueue);
	        connection.start();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @PreDestroy
    public void dispose() {
		if (connection != null) {
			try {
				connection.close();
				session.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
    }
    
    public String execute(KieReleaseId releaseId, Command<?> command) throws JMSException {
    	String uuid = UUID.randomUUID().toString();
    	JaxbCommandsRequest req = new JaxbCommandsRequest(releaseId.toString(), command);
    	BytesMessage byteMessage = null;
    	byteMessage = session.createBytesMessage();
    	
    	try {
			String msgStr = JaxbSerializationProvider.convertJaxbObjectToString(req);
			byteMessage.writeUTF(msgStr);
			byteMessage.setJMSCorrelationID(uuid);
			byteMessage.setJMSReplyTo(responseQueue);
			producer.send(byteMessage);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return uuid;
    }
    
	public Object pollResponse(String correlation) {
		final String correlationSelector = "JMSCorrelationID = '" + correlation + "'";
		
		try {
			connection.start();
			MessageConsumer consumer = session.createConsumer(responseQueue, correlationSelector);
			Message response = consumer.receive(10000);

			if(response == null) {
				LOG.debug("Message not yet recieved: "+correlation);
				return null;
			}
			else {
				LOG.debug("Recieved message for correlation: "+correlation);
				return ((ObjectMessage)response).getObject();
			}
		}
		catch (JMSException e) {
			throw new CommandException("Exception receiving Command Message Response.", e);
		} 
	}
}

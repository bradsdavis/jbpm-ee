package org.jbpm.ee.jms;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.xml.bind.JAXBException;

import org.jbpm.ee.service.ExecutorServiceBean;
import org.jbpm.ee.service.RuntimeServiceBean;
import org.jbpm.ee.support.JaxbSerializationUtil;
import org.jbpm.ee.support.KieReleaseId;
import org.jbpm.services.task.commands.TaskCommand;
import org.kie.api.command.Command;
import org.kie.services.client.serialization.jaxb.JaxbCommandsRequest;
import org.kie.services.client.serialization.jaxb.JaxbCommandsResponse;
import org.kie.services.client.serialization.jaxb.JaxbSerializationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(name = "BaseCommandMDB", activationConfig = {
		 @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		 @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/BaseCommandRequestQueue")
})
public class BaseCommandMDB implements MessageListener {

	private static final Logger LOG = LoggerFactory.getLogger(BaseCommandMDB.class);
	
	@Resource(mappedName = "java:/JmsXA")
	private ConnectionFactory connectionFactory;

	private Connection connection;
	private Session session;
	
	@EJB
	private ExecutorServiceBean executorService;
	
    @PostConstruct
    public void init() throws JMSException {
        connection = connectionFactory.createConnection();
    }
	
	@Override
	public void onMessage(Message message) {
		BytesMessage bytesMessage = (BytesMessage) message;
		
		try {
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			String correlation = bytesMessage.getJMSCorrelationID();
			Destination responseQueue = bytesMessage.getJMSReplyTo();
			
			String serializedRequest = bytesMessage.readUTF();
			JaxbCommandsRequest request = 
					(JaxbCommandsRequest) JaxbSerializationProvider.convertStringToJaxbObject(serializedRequest);
			JaxbCommandsResponse jaxbResponse = new JaxbCommandsResponse(request);
			Long processInstanceId = request.getProcessInstanceId();
			KieReleaseId releaseId = new KieReleaseId(request.getDeploymentId());
			executorService.setDeployment(releaseId, processInstanceId);
			
			List<Command<?>> commands = request.getCommands();
			if (commands != null) {
				for (int i = 0; i < commands.size(); ++i) {
					Object cmdResult = null;
					Command<?> cmd = commands.get(i);
					if( cmd instanceof TaskCommand<?> ) {
						cmdResult = executorService.executeTask(cmd);
					} else {
						cmdResult = executorService.execute(cmd);
					}
					
					if (cmdResult != null) {
						jaxbResponse.addResult(cmdResult, i, cmd);
					}
				}
				
			}

			
			
			MessageProducer producer = session.createProducer(responseQueue);
			ObjectMessage responseMessage = session.createObjectMessage();
			responseMessage.setObject(correlation);
			responseMessage.setJMSCorrelationID(correlation);
			LOG.info("Sending message");
			producer.send(responseMessage);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

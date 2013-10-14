package org.jbpm.workitem.camel.producer;

import org.jbpm.workitem.camel.request.FTPRequestPayloadMapper;
import org.jbpm.workitem.camel.request.RequestPayloadMapper;
import org.jbpm.workitem.camel.uri.FTPURIMapper;
import org.jbpm.workitem.camel.uri.FileURIMapper;
import org.jbpm.workitem.camel.uri.GenericURIMapper;
import org.jbpm.workitem.camel.uri.HTTPURIMapper;
import org.jbpm.workitem.camel.uri.JMSURIMapper;
import org.jbpm.workitem.camel.uri.SQLURIMapper;
import org.jbpm.workitem.camel.uri.XSLTURIMapper;

public class CamelProducerHandlerFactory {
	
	public static CamelProducerHandler sftpProducerHandler() {
		return new CamelProducerHandler(new FTPURIMapper("sftp"), new FTPRequestPayloadMapper("payload"));
	}
	
	public static CamelProducerHandler ftpProducerHandler() {
		return new CamelProducerHandler(new FTPURIMapper("ftp"), new FTPRequestPayloadMapper("payload"));
	}
	
	public static CamelProducerHandler ftpsProducerHandler() {
		return new CamelProducerHandler(new FTPURIMapper("ftps"), new FTPRequestPayloadMapper("payload"));
	}
	
	public static CamelProducerHandler fileProducerHandler() {
		return new CamelProducerHandler(new FileURIMapper(), new RequestPayloadMapper("payload"));
	}

	public static CamelProducerHandler xsltProducerHandler() {
		return new CamelProducerHandler(new XSLTURIMapper(), new RequestPayloadMapper("payload"));
	}
	
	public static CamelProducerHandler jmsProducerHandler() {
		return new CamelProducerHandler(new JMSURIMapper(), new RequestPayloadMapper("payload"));
	}

	public static CamelProducerHandler httpProducerHandler() {
		return new CamelProducerHandler(new HTTPURIMapper(), new RequestPayloadMapper("payload"));
	}
	
	public static CamelProducerHandler sqlProducerHandler() {
		return new CamelProducerHandler(new SQLURIMapper(), new RequestPayloadMapper("payload"));
	}
	
	public static CamelProducerHandler genericProducerHandler(String schema, String pathLocation) {
		return new CamelProducerHandler(new GenericURIMapper(schema, pathLocation), new RequestPayloadMapper("payload"));
	}
	
}

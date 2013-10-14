package org.jbpm.workitem.camel.uri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class FTPURIMapper extends URIMapper {

	public FTPURIMapper(String schema) {
		super(schema);
	}

	@Override
	public URI toURI(Map<String, Object> options) throws URISyntaxException {
		String hostname = (String) options.get("hostname");
		String username = (String) options.get("username");
		String port = (String) options.get("port");
		
		String path = (username == null ? "" : username +"@");
		path += (hostname == null ? "": hostname);
		path += (port == null ? "" : ":"+port);
		
		options.remove("hostname");
		options.remove("username");
		options.remove("port");
		
		return prepareCamelUri(path, options);
	}


}

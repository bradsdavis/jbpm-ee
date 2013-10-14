package org.jbpm.workitem.camel.uri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.camel.util.URISupport;

public abstract class URIMapper {

	protected String schema;
	
	public URIMapper(String schema) {
		this.schema = schema;
	}
	
	public abstract URI toURI(Map<String, Object> options) throws URISyntaxException;
	

	protected URI prepareCamelUri(String path, Map<String, Object> options) throws URISyntaxException {
		String query = URISupport.createQueryString(options);
		URI camelUri = new URI(schema + "://" + path);
		if(options.size() > 0) {
			return URISupport.createURIWithQuery(camelUri, query);
		}
		return camelUri;
	}
	
}

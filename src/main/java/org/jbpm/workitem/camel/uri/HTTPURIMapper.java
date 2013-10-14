package org.jbpm.workitem.camel.uri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class HTTPURIMapper extends URIMapper {

	public HTTPURIMapper() {
		super("http");
	}

	@Override
	public URI toURI(Map<String, Object> options) throws URISyntaxException {
		String address = (String) options.get("address");
		options.remove("address");

		String path = address;
		return prepareCamelUri(path, options);
	}

}

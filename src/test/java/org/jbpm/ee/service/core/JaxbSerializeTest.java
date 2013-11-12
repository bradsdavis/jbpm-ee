package org.jbpm.ee.service.core;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.jbpm.ee.support.KieReleaseId;
import org.junit.Before;
import org.junit.Test;

public class JaxbSerializeTest {

	private JAXBContext context;
	private Marshaller marshaller;

	@Before
	public void setUp() throws Exception
	{
	    context = JAXBContext.newInstance(KieReleaseId.class);
	    marshaller = context.createMarshaller();
	}
	
	@Test
	public void testName() throws Exception {
		KieReleaseId kid = new KieReleaseId();
		kid.setArtifactId("Artifact");
		kid.setGroupId("Group");
		kid.setVersion("Version");
		
		StringWriter writer = new StringWriter();
		marshaller.marshal(kid, writer);
		
		System.out.println(writer);
	}
}

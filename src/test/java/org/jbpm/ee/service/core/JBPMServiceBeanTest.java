package org.jbpm.ee.service.core;

import java.io.InputStream;

import javax.ejb.EJB;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.osgi.spi.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class JBPMServiceBeanTest {

	private static final Logger LOG = LoggerFactory.getLogger(JBPMServiceBeanTest.class);
	
	@EJB
	JBPMServiceBean jbpmServiceBean;
	
	@Deployment
	public static WebArchive createDeployment() throws Exception {
		MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");


		final WebArchive archive = ShrinkWrap.create(WebArchive.class, "test-jbpm-services.war");
		archive.addAsManifestResource("jbossas-ds.xml");
		archive.setManifest(new Asset() {  
	         public InputStream openStream() {  
	             OSGiManifestBuilder builder = OSGiManifestBuilder.newInstance();  
	             builder.addManifestHeader("Dependencies", "org.osgi.core");  
	             return builder.openStream();  
	         }  
	     });  
		
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "test-jbpm-services.jar")
            .addPackages(true, "org.jbpm")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsManifestResource("test-persistence.xml", "persistence.xml")
            
            .addAsManifestResource("META-INF/jbpm-task-orm.xml", "jbpm-task-orm.xml")
            .addAsManifestResource("META-INF/jbpm-main-orm.xml", "jbpm-main-orm.xml")
            
        	.addAsResource("test-changeset.xml", "jbpm-changeset.xml");
        
		archive.addAsLibraries(jar);
		archive.addAsLibraries(resolver.artifact("org.jbpm:jbpm-flow").resolveAsFiles());
		archive.addAsLibraries(resolver.artifact("org.jbpm:jbpm-flow-builder").resolveAsFiles());
		archive.addAsLibraries(resolver.artifact("org.jbpm:jbpm-bpmn2").resolveAsFiles());
		archive.addAsLibraries(resolver.artifact("org.jbpm:jbpm-persistence-jpa").resolveAsFiles());
		archive.addAsLibraries(resolver.artifact("org.jbpm:jbpm-human-task-core").resolveAsFiles());
		archive.addAsLibraries(resolver.artifact("org.quartz-scheduler:quartz").resolveAsFiles());
		
		return archive;
	}
	
	
	@Test
	public void testJBPMServiceLookup() throws Exception {
		Assert.assertTrue(jbpmServiceBean != null);
		
		LOG.info("Hello world!");
		StatefulKnowledgeSession sns = jbpmServiceBean.getKnowledgeSession();
		LOG.info(sns.toString());
		Assert.assertNotNull(sns);
		
	}
}

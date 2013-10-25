package org.jbpm.ee.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import org.jbpm.ee.service.remote.RuntimeServiceRemote;
import org.jbpm.ee.support.KieReleaseId;

@Stateful
@LocalBean
public class RuntimeServiceBean  implements RuntimeServiceRemote{

	@Override
	public void setRuntime(KieReleaseId releaseId) {
		// TODO Auto-generated method stub
		
	}

}

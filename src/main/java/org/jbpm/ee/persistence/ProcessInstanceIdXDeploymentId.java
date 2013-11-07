package org.jbpm.ee.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Version;

import org.jbpm.persistence.processinstance.ProcessInstanceInfo;

@Entity
public class ProcessInstanceIdXDeploymentId {

	
	  @Id @OneToOne
	  @PrimaryKeyJoinColumn
	  private ProcessInstanceInfo processInstanceInfo;
	  
	  @Version
	  @Column(name = "OPTLOCK")
	  private int version;
	  
	  private String deploymentId;
	  
	  public ProcessInstanceIdXDeploymentId() {}
	  
	  public ProcessInstanceIdXDeploymentId(ProcessInstanceInfo processInstanceInfo, String deploymentId) {
		  this.processInstanceInfo = processInstanceInfo;
		  this.deploymentId = deploymentId;
	  }
	  
	  public ProcessInstanceInfo getProcessInstanceInfo() {
		  return processInstanceInfo;
	  }
	  
	  public String getDeploymentId() {
		  return deploymentId;
	  }
}

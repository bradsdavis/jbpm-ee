package org.jbpm.ee.service.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=false)
public class InactiveProcessInstance extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4248542124773720848L;
	private Long processInstanceId;
	
	public InactiveProcessInstance(Long processInstanceId, String message, Throwable t) {
		super(message, t);
		this.processInstanceId = processInstanceId;
	}

	public InactiveProcessInstance(Long processInstanceId) {
		super("Inactive process instance: "+processInstanceId);
		this.processInstanceId = processInstanceId;
	}
	
	public Long getProcessInstanceId() {
		return processInstanceId;
	}
	
}

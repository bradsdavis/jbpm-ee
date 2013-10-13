package org.jbpm.ee.exception;

public class SessionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4754610334273329591L;

	public SessionException(String message, Throwable t) {
		super(message, t);
	}
}

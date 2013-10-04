package org.jbpm.ee.exception;

public class SessionException extends RuntimeException {

	public SessionException(String message, Throwable t) {
		super(message, t);
	}
}

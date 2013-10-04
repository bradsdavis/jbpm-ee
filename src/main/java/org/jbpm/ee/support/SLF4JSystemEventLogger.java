package org.jbpm.ee.support;

import org.drools.SystemEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JSystemEventLogger implements SystemEventListener {

	private static final Logger LOG = LoggerFactory
			.getLogger(SLF4JSystemEventLogger.class);

	public void debug(String message) {
		LOG.debug(message);
	}

	public void debug(String message, Object object) {
		LOG.debug(message + " object=" + object);
	}

	public void exception(String message, Throwable e) {
		LOG.error(message, e);
	}

	public void exception(Throwable e) {
		LOG.error("", e);
	}

	public void info(String message) {
		LOG.info(message);
	}

	public void info(String message, Object object) {
		LOG.info(message + " object=" + object);
	}

	public void warning(String message) {
		LOG.warn(message);
	}

	public void warning(String message, Object object) {
		LOG.warn(message+" object="+object);
	}

}

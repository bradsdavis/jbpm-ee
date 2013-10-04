package org.jbpm.ee.support.timer;

import org.drools.time.impl.DefaultJobHandle;
import org.quartz.JobKey;

public class QuartzJobHandle extends DefaultJobHandle {

	private final String name;
	private final String group;
	
	public QuartzJobHandle(long id, String name, String group) {
		super(id);
		
		this.name = name;
		this.group = group;
	}

	public String getName() {
		return name;
	}
	
	public String getGroup() {
		return group;
	}
	
	public JobKey toQuartzJobKey() {
		return new JobKey(this.name, this.group);
	}
}

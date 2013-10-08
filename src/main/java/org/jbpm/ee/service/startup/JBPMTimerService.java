package org.jbpm.ee.service.startup;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.drools.command.CommandService;
import org.drools.time.InternalSchedulerService;
import org.drools.time.Job;
import org.drools.time.JobContext;
import org.drools.time.JobHandle;
import org.drools.time.Trigger;
import org.drools.time.impl.TimerJobFactoryManager;
import org.drools.time.impl.TimerJobInstance;
import org.jbpm.ee.support.timer.QuartzJobHandle;
import org.jbpm.process.instance.timer.TimerManager.ProcessJobContext;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Startup
@Singleton
public class JBPMTimerService implements org.drools.time.TimerService, TimerJobFactoryManager {
	private static final Logger LOG = LoggerFactory.getLogger(JBPMTimerService.class);
	
	private Scheduler scheduler;
	
	@PostConstruct
	private void setup() {
		SchedulerFactory factory = new StdSchedulerFactory();
		try {
			scheduler = factory.getScheduler();
			scheduler.start();
		}
		catch(Exception e) {
			LOG.error("Exception starting scheduler.", e);
		}
	}
	
	
	@Override
	public JobHandle scheduleJob(Job job, JobContext ctx, Trigger trigger) {
        StringBuilder jobName = new StringBuilder();
        UUID uuid = UUID.randomUUID();
        

        if (ctx instanceof ProcessJobContext) {
            ProcessJobContext processCtx = (ProcessJobContext) ctx;
            jobName.append("Process-");
            jobName.append(processCtx.getProcessInstanceId());
        } else {
            jobName.append("Timer-");
            jobName.append(ctx.getClass().getSimpleName());
        }
        jobName.append("-"+uuid.toString());
        
        //create one the job.
        QuartzJobHandle jobHandle = new QuartzJobHandle(0, jobName.toString(), "jbpm");
        
        TimerJobInstance jobInstance = createTimerJobInstance( job, ctx, trigger, jobHandle, (InternalSchedulerService) this );
        jobHandle.setTimerJobInstance( (TimerJobInstance) jobInstance );

        //schedule the job to execute.
        internalSchedule(jobInstance);
        
        return jobHandle;
	}

	@Override
	public boolean removeJob(JobHandle jobHandle) {
       QuartzJobHandle quartzJobHandle = (QuartzJobHandle) jobHandle;
        try {
            boolean removed = scheduler.deleteJob(quartzJobHandle.toQuartzJobKey());            
            return removed;
        } catch (SchedulerException e) {     
            throw new RuntimeException("Exception while removing job", e);
        } 
	}

	@Override
	public long getCurrentTime() {
		//return now.
		return (new Date()).getTime();
	}

	@Override
	public void shutdown() {
		//do nothing.  since this is a singleton service, it only gets shut down when the timer service ends.
	}

	@Override
	public long getTimeToNextJob() {
		//not used within jBPM 5.
		return 0;
	}

	@Override
	public Collection<TimerJobInstance> getTimerJobInstances() {
		//only used to persist timers in jBPM 5 (when serializing sessions).  return empty.
		return new LinkedList<TimerJobInstance>();
	}


	@Override
	public TimerJobInstance createTimerJobInstance(Job job, JobContext ctx, Trigger trigger, JobHandle handle, InternalSchedulerService scheduler) {
		// TODO
		
		return null;
	}


	@Override
	public void addTimerJobInstance(TimerJobInstance instance) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeTimerJobInstance(TimerJobInstance instance) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setCommandService(CommandService commandService) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public CommandService getCommandService() {
		// TODO Auto-generated method stub
		return null;
	}

    public void internalSchedule(TimerJobInstance timerJobInstance) {
        QuartzJobHandle quartzJobHandle = (QuartzJobHandle) timerJobInstance.getJobHandle();
        
        // Schedule the job with the trigger
        try {
        	this.addTimerJobInstance( timerJobInstance );

        	TriggerKey triggerKey = new TriggerKey(quartzJobHandle.getName()+"_trigger", quartzJobHandle.getGroup());
            org.quartz.Trigger trigger = TriggerBuilder.newTrigger()
            	    .withIdentity(triggerKey)
            	    .forJob(quartzJobHandle.toQuartzJobKey())
            	    .build();

            JobDetail jobDetail = JobBuilder.newJob()
            		.withIdentity(quartzJobHandle.toQuartzJobKey())
            		.build();
            jobDetail.getJobDataMap().put("timerJobInstance", timerJobInstance);

            if (jobDetail == null) {
                scheduler.scheduleJob(jobDetail, trigger);
            } 
            else {
                // need to add the job again to replace existing especially important if jobs are persisted in db
                scheduler.addJob(jobDetail, true);
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception while scheduling job", e);
        }
    }

	
	
}

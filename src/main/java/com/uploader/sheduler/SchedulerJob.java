
package com.uploader.sheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


public class SchedulerJob extends QuartzJobBean {

    private SchedulerTask schedulerTask;

    public void setSchedulerTask(SchedulerTask schedulerTask) {
        this.schedulerTask = schedulerTask;
    }

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        schedulerTask.cleanLinks();
    }
}

package _quartz.step01;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import _basic.utils.LocaleDate;

public class SimpleJob implements Job {

	public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
		System.out.println(jobCtx.getJobDetail().getKey() + " triggered. time is" + new LocaleDate());
	}

}

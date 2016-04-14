package _quartz.step09;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

/**
 * @author wkratzer
 */
public class Job1Listener implements JobListener {

	static Logger _log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	public String getName() {
		return "job1_to_job2";
	}

	public void jobToBeExecuted(JobExecutionContext inContext) {
		_log.info("Job1Listener says: Job Is about to be executed.");
	}

	public void jobExecutionVetoed(JobExecutionContext inContext) {
		_log.info("Job1Listener says: Job Execution was vetoed.");
	}

	public void jobWasExecuted(JobExecutionContext inContext,
			JobExecutionException inException) {
		_log.info("Job1Listener says: Job was executed.");

		// Simple job #2
		JobDetail job2 = newJob(SimpleJob2.class).withIdentity("job2").build();

		Trigger trigger = newTrigger().withIdentity("job2Trigger").startNow()
				.build();

		try {
			// schedule the job to run!
			inContext.getScheduler().scheduleJob(job2, trigger);
		} catch (SchedulerException e) {
			_log.warn("Unable to schedule job2!");
			e.printStackTrace();
		}

	}

}

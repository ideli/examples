package _quartz.step10;

import java.util.Date;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * <p>
 * This is just a simple job that gets fired off many times by example 1
 * </p>
 * 
 * @author Bill Kratzer
 */
public class SimpleJob implements Job {

	static Logger _log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	/**
	 * Empty constructor for job initilization
	 */
	public SimpleJob() {
	}

	/**
	 * <p>
	 * Called by the <code>{@link org.quartz.Scheduler}</code> when a
	 * <code>{@link org.quartz.Trigger}</code> fires that is associated with the
	 * <code>Job</code>.
	 * </p>
	 * 
	 * @throws JobExecutionException
	 *             if there is an exception while executing the job.
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		// This job simply prints out its job name and the
		// date and time that it is running
		JobKey jobKey = context.getJobDetail().getKey();
		_log.info("Executing job: " + jobKey + " executing at " + new Date()
				+ ", fired by: " + context.getTrigger().getKey());

		if (context.getMergedJobDataMap().size() > 0) {
			Set<String> keys = context.getMergedJobDataMap().keySet();
			for (String key : keys) {
				String val = context.getMergedJobDataMap().getString(key);
				_log.info(" - jobDataMap entry: " + key + " = " + val);
			}
		}

		context.setResult("hello");
	}

}

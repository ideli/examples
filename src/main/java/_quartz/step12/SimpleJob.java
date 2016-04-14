package _quartz.step12;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * <p>
 * A dumb implementation of Job, for unittesting purposes.
 * </p>
 * 
 * @author James House
 */
public class SimpleJob implements Job {

	public static final String MESSAGE = "msg";

	static Logger _log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	/**
	 * Quartz requires a public empty constructor so that the scheduler can
	 * instantiate the class whenever it needs.
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

		String message = (String) context.getJobDetail().getJobDataMap()
				.get(MESSAGE);

		_log.info("SimpleJob: " + jobKey + " executing at " + new Date());
		_log.info("SimpleJob: msg: " + message);
	}

}

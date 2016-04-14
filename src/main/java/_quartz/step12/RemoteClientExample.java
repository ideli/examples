package _quartz.step12;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * This example is a client program that will remotely talk to the scheduler to
 * schedule a job. In this example, we will need to use the JDBC Job Store. The
 * client will connect to the JDBC Job Store remotely to schedule the job.
 * 
 * @author James House, Bill Kratzer
 */
public class RemoteClientExample {

	public void run() throws Exception {

		Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

		// First we must get a reference to a scheduler
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		// define the job and ask it to run
		JobDetail job = newJob(SimpleJob.class).withIdentity(
				"remotelyAddedJob", "default").build();

		JobDataMap map = job.getJobDataMap();
		map.put("msg", "Your remotely added job has executed!");

		Trigger trigger = newTrigger()
				.withIdentity("remotelyAddedTrigger", "default")
				.forJob(job.getKey())
				.withSchedule(cronSchedule("/5 * * ? * *")).build();

		// schedule the job
		sched.scheduleJob(job, trigger);

		log.info("Remote job scheduled.");
	}

	public static void main(String[] args) throws Exception {

		RemoteClientExample example = new RemoteClientExample();
		example.run();
	}

}

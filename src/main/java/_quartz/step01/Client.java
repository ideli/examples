package _quartz.step01;

import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.JobDetail;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Client {

	public static void main(String[] args) throws SchedulerException {
		Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

		JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
				.withIdentity("job1", "group1").build();
		Date runTime = DateBuilder.evenMinuteDate(new Date());
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger1", "group1").startAt(runTime).build();

		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		sched.scheduleJob(jobDetail, trigger);
		//System.out.println(jobDetail.getKey() + " will run at: " + runTime);
		logger.info(jobDetail.getKey() + " will run at: " + runTime);
		sched.start();

		try {
			// wait 65 seconds to show job
			Thread.sleep(65L * 1000L);
			// executing...
		} catch (Exception e) {
		}

		// shut down the scheduler
		logger.info("------- Shutting Down ---------------------");
		sched.shutdown(true);
	}
}

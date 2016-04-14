package _quartz.step12;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Bill Kratzer
 */
public class RemoteServerExample {

	/**
	 * This example will spawn a large number of jobs to run
	 * 
	 * @author James House, Bill Kratzer
	 */
	public void run() throws Exception {
		Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

		// First we must get a reference to a scheduler
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		log.info("------- Initialization Complete -----------");

		log.info("------- (Not Scheduling any Jobs - relying on a remote client to schedule jobs --");

		log.info("------- Starting Scheduler ----------------");

		// start the schedule
		sched.start();

		log.info("------- Started Scheduler -----------------");

		log.info("------- Waiting ten minutes... ------------");

		// wait five minutes to give our jobs a chance to run
		try {
			Thread.sleep(600L * 1000L);
		} catch (Exception e) {
			//
		}

		// shut down the scheduler
		log.info("------- Shutting Down ---------------------");
		sched.shutdown(true);
		log.info("------- Shutdown Complete -----------------");

		SchedulerMetaData metaData = sched.getMetaData();
		log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
	}

	public static void main(String[] args) throws Exception {

		RemoteServerExample example = new RemoteServerExample();
		example.run();
	}

}

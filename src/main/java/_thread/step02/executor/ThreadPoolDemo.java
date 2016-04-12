package _thread.step02.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {

	public void test() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("thread run");
			}
		});
		//newCachedThreadPool 不限制开启线程数,可能造成同时开启线程数过多问题
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.execute(t);
		pool.shutdown();
		
		//newFixedThreadPool 线程数固定,可能造成同时等待执行的任务数过多而内存耗尽
		pool = Executors.newFixedThreadPool(100);
		pool.execute(t);
		pool.shutdown();
		
		int corePoolSize = 10;  //最小线程数
		int maximumPoolSize = 100;  //最大线程数
		long keepAliveTime = 100;  //线程池维护线程所允许的空闲时间
		TimeUnit unit = TimeUnit.SECONDS;  //线程池维护线程所允许的空闲时间的单位
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);  //线程池所使用的缓冲队列
		//线程池对拒绝任务的处理策略
		RejectedExecutionHandler handler = new RejectedExecutionHandler(){
			@Override
			public void rejectedExecution(Runnable r,
					ThreadPoolExecutor executor) {
				new Thread(r, "exception by pool").start();
			}
		};
		pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
		pool.execute(t);
		pool.shutdown();
	}
}

package _thread.step10.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/*
 * 控制访问资源线程数量
 */
public class SemaphoreDemo {

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		//只能5个线程同时访问
		final Semaphore semp = new Semaphore(5);
		//模拟20个客户端
		for (int index = 0; index < 20; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				public void run() {
					System.out.println("Thread " + NO + ":semp.availablePermits()=" + semp.availablePermits());
					try {
						semp.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Thread " + NO + " acquired");
					try {
						Thread.sleep((long) (Math.random() * 10000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// 访问完后释放
					semp.release();
					System.out.println("Thread " + NO + ":semp.availablePermits()=" + semp.availablePermits());
				}
			};
			exec.execute(run);
		}
		exec.shutdown();
	}
}

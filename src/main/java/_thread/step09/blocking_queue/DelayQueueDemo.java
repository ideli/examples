package _thread.step09.blocking_queue;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DelayQueueDemo {

	public static void main(String[] args) {
		Random rand = new Random(47);
		ExecutorService exec = Executors.newCachedThreadPool();
		DelayQueue<DelayedTask> queue = new DelayQueue<>();
		for(int i = 0; i < 20; i++) {
			queue.put(new DelayedTask(rand.nextInt(5000)));
		}
		exec.execute(new DelayedTaskConsumer(queue));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		exec.shutdownNow();
	}
}

class DelayedTaskConsumer implements Runnable {

	private DelayQueue<DelayedTask> q;
	
	public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
		this.q = q;
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				q.take().run();
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}
		System.out.println("Finished DelayedTaskConsumer");
	}
	
}

class DelayedTask implements Delayed, Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final long delta; //增量
	private final long trigger;
	
	public DelayedTask(long delayInMilliseconds) {
		delta = delayInMilliseconds;
		//转化为纳秒(当前时间+增量)
		trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
	}

	@Override
	public int compareTo(Delayed args) {
		DelayedTask that = (DelayedTask)args;
		if(trigger < that.trigger) return -1;
		if(trigger > that.trigger) return 1;
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
	}
	
	public String toString() {
		return "DelayedTask " + id + ":" + delta;
	}

	@Override
	public void run() {
		System.out.println(this);
	}
}
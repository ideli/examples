package _thread.step07.latch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {

	public static void main(String[] args) {
		final int SIZE = 100;
		ExecutorService exec = Executors.newCachedThreadPool();
		CountDownLatch latch = new CountDownLatch(SIZE);
		for(int i = 0; i < 10; i++) {
			exec.execute(new WaitingTask(latch));
		}
		for(int i = 0; i < SIZE; i++) {
			exec.execute(new TaskPortion(latch));
		}
		System.out.println("Launched all tasks");
		exec.shutdown();
	}
}

class TaskPortion implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private static Random rand = new Random(47);
	private final CountDownLatch latch;
	TaskPortion(CountDownLatch latch) {
		this.latch = latch;
	}
	
	@Override
	public void run() {
		doWork();
		latch.countDown();
	}
	
	public void doWork() {
		try {
			TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
			System.out.println(this + " completed");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public String toString() {
		return String.format("TaskPortion %1S", id);
	}
}

class WaitingTask implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final CountDownLatch latch;
	WaitingTask(CountDownLatch latch) {
		this.latch = latch;
	}
	
	@Override
	public void run() {
		try {
			latch.await();
			System.out.println("latch.await() for " + this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public String toString() {
		return String.format("WaitingTask %1S", id);
	}
}

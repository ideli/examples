package _thread.step09.blocking_queue;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueDemo {

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<>();
		exec.execute(new PrioritizedTaskProducer(queue));
		exec.execute(new PrioritizedTaskConsumer(queue));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		exec.shutdownNow();
	}
}

class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {

	private Random rand = new Random(47);
	private static int counter = 0;
	private final int id = counter++;
	private final int priority;
	
	public PrioritizedTask(int priority) {
		this.priority = priority;
	}
	
	@Override
	public int compareTo(PrioritizedTask arg0) {
		return priority < arg0.priority ? 1 : (priority > arg0.priority ? -1 : 0);
	}

	@Override
	public void run() {
		try {
			TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(this);
	}
	
	public String toString() {
		return "Task " + id + "[priority=" + this.priority + "]";
	}
}

class PrioritizedTaskProducer implements Runnable {
	private Random rand = new Random();
	private Queue<Runnable> queue;
	public PrioritizedTaskProducer(Queue<Runnable> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		for(int i = 0; i < 20; i++) {
			queue.add(new PrioritizedTask(rand.nextInt(10)));
		}
		try {
			for(int i = 0; i < 10; i++) {
				TimeUnit.MILLISECONDS.sleep(250);
				queue.add(new PrioritizedTask(10));
			}
			for(int i = 0; i < 10; i++) {
				queue.add(new PrioritizedTask(i));
			}
		} catch(InterruptedException e) {
			
		}
		System.out.println("Finished PrioritizedTaskProducer");
	}
}

class PrioritizedTaskConsumer implements Runnable {

	private PriorityBlockingQueue<Runnable> queue;
	public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				queue.take().run();
			}
		} catch(InterruptedException e) {
			
		}
		System.out.println("Finished PrioritizedTaskConsumer");
	}
	
}
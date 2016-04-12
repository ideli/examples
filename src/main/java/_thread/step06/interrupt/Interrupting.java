package _thread.step06.interrupt;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Interrupting {

	private static ExecutorService exec = Executors.newCachedThreadPool();
	
	static void test(Runnable r) throws InterruptedException {
		Future<?> f = exec.submit(r);
		TimeUnit.MILLISECONDS.sleep(100);
		System.out.println("Inerrupting " + r.getClass().getName());
		f.cancel(true);
	}
	
	public static void main(String[] args) throws InterruptedException {
		test(new SleepBlocked());
		System.out.println("---------------------------");
		test(new IOBlocked());
		System.out.println("---------------------------");
		test(new SynchronizedBlocked());
		System.out.println("---------------------------");
		test(new LockBlocked());
		TimeUnit.SECONDS.sleep(3);
		System.out.println("---------------------------");
		System.out.println("Aborting with System.exit(0)");
		System.exit(0);
	}
}

/*
 * 可中断阻塞
 */
class SleepBlocked implements Runnable {

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(100);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
		}
		System.out.println("Exiting SleepBlocked.run()");
	}
}

/*
 * 不可中断阻塞
 */
class IOBlocked implements Runnable {

	@Override
	public void run() {
		System.out.println("Waiting for read()");
		try {
			System.in.read();
		} catch (IOException e) {
			if(Thread.currentThread().isInterrupted()) {
				System.out.println("interrupted from blocked I/O");
			} else {
				throw new RuntimeException(e);
			}
		}
		System.out.println("Exiting IOBlocked.run()");
	}
}

/*
 * 不可中断阻塞
 */
class SynchronizedBlocked implements Runnable {
	
	public synchronized void f() {
		while(true)
			Thread.yield();
	}
	
	public SynchronizedBlocked() {
		new Thread() {
			public void run() {
				f();
			}
		}.start();
	}

	@Override
	public void run() {
		System.out.println("Trying to call f()");
		f();
		System.out.println("Exiting SynchronizedBlocked.run()");
	}
}

/*
 * 可中断阻塞
 */
class LockBlocked implements Runnable {
	
	private Lock lock = new ReentrantLock();
	
	public void l() {
		try {
			lock.lockInterruptibly();
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
		}
	}
	
	public LockBlocked() {
		lock.lock();
	}

	@Override
	public void run() {
		System.out.println("Trying to call l()");
		l();
		System.out.println("Exiting LockBlocked.run()");
	}
}

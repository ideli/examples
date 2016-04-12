package _thread.step04.catch_exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ExceptionThread implements Runnable {

	@Override
	public void run() {
		throw new RuntimeException();
	}
	
	public static void main(String[] args) {
		Thread t = new Thread(new ExceptionThread());
		
		ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
		exec.execute(t);
		exec.shutdown();
		
		exec = Executors.newCachedThreadPool();
		exec.execute(t);
		
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread.setDefaultUncaughtExceptionHandler(new MyCaughtExceptionHandler());
		exec.execute(t);
		exec.shutdown();
	}

}

class MyCaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("caught " + e);
	}
}

class HandlerThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setUncaughtExceptionHandler(new MyCaughtExceptionHandler());
		return t;
	}
	
}

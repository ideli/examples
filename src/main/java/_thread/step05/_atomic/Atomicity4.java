package _thread.step05._atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class Atomicity4 implements Runnable {
	
	private AtomicInteger i = new AtomicInteger();
	
	public int getValue() { return i.get(); }
		
	private void evenIncrement() { i.addAndGet(2); }
		
	@Override
	public void run() {
		while(true)
			evenIncrement();
	}
}

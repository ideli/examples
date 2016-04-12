package _thread.step05._atomic;

public class Atomicity3 implements Runnable {
	
	private int i = 0;
	
	public synchronized int getValue() { return i; }
		
	private synchronized void evenIncrement() { i++; i++; }
		
	@Override
	public void run() {
		while(true)
			evenIncrement();
	}
}

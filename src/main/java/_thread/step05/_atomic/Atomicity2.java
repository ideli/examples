package _thread.step05._atomic;

public class Atomicity2 implements Runnable {
	
	private volatile int i = 0;
	
	public int getValue() { return i; }
		
	private synchronized void evenIncrement() { i++; i++; }
		
	@Override
	public void run() {
		while(true)
			evenIncrement();
	}
}

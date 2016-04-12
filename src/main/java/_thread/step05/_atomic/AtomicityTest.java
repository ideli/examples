package _thread.step05._atomic;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtomicityTest {
	
	/*
	 * 将会获得奇数退出
	 */
	public void show1() {
		ExecutorService exec = Executors.newCachedThreadPool();
		Atomicity1 at1 = new Atomicity1();
		exec.execute(at1);
		boolean running = true;
		while(running) {
			int val = at1.getValue();
			if(val % 2 != 0) {
				System.out.println("Atomicity1 stop at " + val);
				running = false;
			}
		}
	}
	
	/*
	 * 添加volatile 仍会获得奇数退出
	 */
	public void show2() {
		ExecutorService exec = Executors.newCachedThreadPool();
		Atomicity2 at2 = new Atomicity2();
		exec.execute(at2);
		
		boolean running = true;
		while(running) {
			int val = at2.getValue();
			if(val % 2 != 0) {
				System.out.println("Atomicity2 stop at " + val);
				running = false;
			}
		}
	}
	
	/*
	 * 添加synchronized 不会获得奇数
	 */
	public void show3() {
		ExecutorService exec = Executors.newCachedThreadPool();
		Atomicity3 at3 = new Atomicity3();
		exec.execute(at3);
		long begin = System.currentTimeMillis();
		boolean running = true;
		while(running) {
			int val = at3.getValue();
			if(val % 2 != 0) {
				System.out.println("Atomicity3 stop at " + val);
			}
			if(System.currentTimeMillis() - begin > 5000) {
				System.out.println("Atomicity3 aborting");
				running = false;
			}
		}
		
	}
	
	/*
	 * 使用AtomicInteger 不会获得奇数
	 * 建议非特殊情况不使用AtomicInteger
	 */
	public void show4() {
		boolean running = true;
		new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
				System.out.println("Atomicity4 aborting");
				System.exit(0);
			}
		}, 5000);
		
		ExecutorService exec = Executors.newCachedThreadPool();
		Atomicity4 at4 = new Atomicity4();
		exec.execute(at4);
		
		while(running) {
			int val = at4.getValue();
			if(val % 2 != 0) {
				System.out.println("Atomicity4 stop at " + val);
				running = false;
			}
		}
	}

	public static void main(String[] args) {
		new AtomicityTest().show1();
		new AtomicityTest().show2();
		new AtomicityTest().show3();
		new AtomicityTest().show4();
	}
}

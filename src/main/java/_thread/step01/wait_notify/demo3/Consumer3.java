package _thread.step01.wait_notify.demo3;

import java.util.concurrent.BlockingQueue;

public class Consumer3 implements Runnable {

	private BlockingQueue<Integer> queue;
	
	public Consumer3(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		System.out.println("消费者开始消费整数...");
		for(int product = 1; product <= 10; product++) {
			try {
				Thread.sleep((int)(Math.random() * 3000)); //暂停随机时间
				this.queue.take();
				System.out.printf("消费者取走 %d%n", product);
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
	}
}

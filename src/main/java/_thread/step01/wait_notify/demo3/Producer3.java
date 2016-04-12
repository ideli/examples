package _thread.step01.wait_notify.demo3;

import java.util.concurrent.BlockingQueue;

public class Producer3 implements Runnable {
	
	private BlockingQueue<Integer> queue;
	
	public Producer3(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		System.out.println("生产者开始生产整数...");
		for(int product = 1; product <= 10; product++) {
			try {
				Thread.sleep((int)(Math.random() * 3000)); //暂停随机时间
				this.queue.put(product);
				System.out.printf("生产者交货 %d%n", product);
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
	}

}

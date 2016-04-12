package _thread.step01.wait_notify.demo1;

public class Consumer1 implements Runnable {

	private Clerk1 clerk;
	
	public Consumer1(Clerk1 clerk) {
		this.clerk = clerk;
	}
	
	@Override
	public void run() {
		System.out.println("消费者开始消费整数...");
		for(int i = 1; i <= 10; i++) {
			/*
			try {
				Thread.sleep((int)(Math.random() * 3000)); //暂停随机时间
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
			*/
			int product = clerk.getProduct();
			System.out.println("消费: " + product);
		}
		
	}

}

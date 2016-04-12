package _thread.step01.wait_notify.demo2;

public class Consumer2 implements Runnable {

	private Clerk2 clerk;
	
	public Consumer2(Clerk2 clerk) {
		this.clerk = clerk;
	}
	
	@Override
	public void run() {
		System.out.println("消费者开始消费整数...");
		for(int i = 1; i <= 10; i++) {
			try {
				Thread.sleep((int)(Math.random() * 3000)); //暂停随机时间
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
			clerk.getProduct();
		}
		
	}
}

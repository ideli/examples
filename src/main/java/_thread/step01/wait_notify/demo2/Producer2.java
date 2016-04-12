package _thread.step01.wait_notify.demo2;

public class Producer2 implements Runnable {

	private Clerk2 clerk;
	
	public Producer2(Clerk2 clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		System.out.println("生产者开始生产整数...");
		for(int product = 1; product <= 10; product++) {
			try {
				Thread.sleep((int)(Math.random() * 3000)); //暂停随机时间
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
			clerk.setProduct(product);
		}
	}
}

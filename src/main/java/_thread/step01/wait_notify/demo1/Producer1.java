package _thread.step01.wait_notify.demo1;

public class Producer1 implements Runnable {
	
	private Clerk1 clerk;
	
	public Producer1(Clerk1 clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		System.out.println("生产者开始生产整数...");
		for(int product = 1; product <= 10; product++) {
			/*
			try {
				Thread.sleep((int)(Math.random() * 3000)); //暂停随机时间
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
			*/
			clerk.setProduct(product);
		}
	}

}

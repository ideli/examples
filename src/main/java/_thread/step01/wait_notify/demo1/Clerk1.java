package _thread.step01.wait_notify.demo1;

public class Clerk1 {

	//店员一次只能持有一个整数
	//大于-1,通知生产者等待,通知消费者可以取货
	//-1,通知生产者可以交货,通知消费者等待
	private int product = -1;
	
	public synchronized void setProduct(int product) {
		while(this.product != -1) { //通常采用循环, 如果条件不满足就继续wait
			try {
				System.out.printf("生产者交货 %d, 没有空间...等待%n", this.product);
				wait();  //目前店员没有空间收产品,请稍候(释放锁)
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		this.product = product; //店员收货
		System.out.printf("生产者交货 %d%n", this.product);
		notify();
	}
	
	public synchronized int getProduct() {
		while(this.product == -1) { //通常采用循环, 如果条件不满足就继续wait
			try {
				System.out.printf("消费者取货 %d, 没有货物...等待%n", this.product);
				wait();  //目前店员没货,请稍候(释放锁)
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		int p = this.product;
		System.out.printf("消费者取走 %d%n", this.product);
		this.product = -1;
		notify();
		return p;
	}
}

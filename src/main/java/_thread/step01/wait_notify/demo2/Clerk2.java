package _thread.step01.wait_notify.demo2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Clerk2 {

	// 店员一次只能持有一个整数
	// 大于-1,通知生产者等待,通知消费者可以取货
	// -1,通知生产者可以交货,通知消费者等待
	private int product = -1;
	
	private Lock lock = new ReentrantLock();
	private Condition producerSet = lock.newCondition();
	private Condition consumerSet = lock.newCondition();

	public void setProduct(int product) {
		try {
			lock.lock();
			while (this.product != -1) {
				try {
					producerSet.await(); // 目前店员没有空间收产品,请稍候
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			this.product = product; // 店员收货
			System.out.printf("生产者交货 %d%n", this.product);
			consumerSet.signal();
		} finally {
			lock.unlock();
		}
	}

	public int getProduct() {
		try {
			lock.lock();
			while (this.product == -1) {
				try {
					consumerSet.await(); // 目前店员没货,请稍候
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			int p = this.product;
			System.out.printf("消费者取走 %d%n", this.product);
			this.product = -1;
			producerSet.signal();
			return p;
		} finally {
			lock.unlock();
		}
	}
}

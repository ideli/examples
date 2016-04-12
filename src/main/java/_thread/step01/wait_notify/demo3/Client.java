package _thread.step01.wait_notify.demo3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client {

	/*
	 * BlockingQueue阻塞队列
	 */
	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
		new Thread(new Producer3(queue)).start();
		new Thread(new Consumer3(queue)).start();
	}
}

package _thread.step01.wait_notify.demo2;

public class Client {

	/*
	 * 使用Lock Condition优化wait-notify机制
	 */
	public static void main(String[] args) {
		Clerk2 clerk = new Clerk2();
		new Thread(new Producer2(clerk)).start();
		new Thread(new Consumer2(clerk)).start();
	}
}

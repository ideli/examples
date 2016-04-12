package _thread.step01.wait_notify.demo1;

public class Client {

	/*
	 * 基础的wait-notify模型
	 */
	public static void main(String[] args) {
		Clerk1 clerk = new Clerk1();
		new Thread(new Producer1(clerk)).start();
		new Thread(new Consumer1(clerk)).start();
	}
}

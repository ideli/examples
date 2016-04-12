package _designPatterns.step01.singleton;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LazySingleton {

	private LazySingleton() {}
	
	private volatile static LazySingleton instance;
	private final static Lock lock = new ReentrantLock();
	
	public static LazySingleton getInstance() {
		if(instance == null) {
			try {
				lock.lock();
				if(instance == null) {
					instance = new LazySingleton();
				}
				
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}
	
	public void show() {
		System.out.println("我是线程安全懒汉单例模式");
	}
}

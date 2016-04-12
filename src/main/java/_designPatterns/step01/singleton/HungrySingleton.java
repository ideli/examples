package _designPatterns.step01.singleton;

public class HungrySingleton {

	private HungrySingleton() {}
	
	private final static HungrySingleton instance = new HungrySingleton();
	
	public final static HungrySingleton getInstance() {
		return instance;
	}
	
	public void show() {
		System.out.println("我是饿汉单例模式");
	}
}

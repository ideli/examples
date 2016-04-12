package _designPatterns.step01.singleton;

/*
 * 单例模式
 */
public class Client {

	public void test() {
		HungrySingleton hungry = HungrySingleton.getInstance();
		hungry.show();
		
		LazySingleton lazy = LazySingleton.getInstance();
		lazy.show();
	}
}

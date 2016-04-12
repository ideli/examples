package _designPatterns.step12.facade;

/*
 * 外观模式
 */
public class Client {

	public void test() {
		Facade f = new Facade();
		f.businessA();
		f.businessB();
	}
}

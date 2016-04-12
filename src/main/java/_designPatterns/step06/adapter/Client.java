package _designPatterns.step06.adapter;

/*
 * 适配器模式
 */
public class Client {

	public void test() {
		Target t = new Adapter();
		t.request();
	}
}

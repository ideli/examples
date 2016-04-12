package _designPatterns.step11.proxy;

/*
 * 代理模式
 */
public class Client {

	public void test() {
		Subject proxy = new Proxy();
		proxy.request();
	}
}

package _designPatterns.step07.decorator;

/*
 * 装饰模式
 */
public class Client {

	public void test() {
		ConcreteComponent component = new ConcreteComponent();
		ConcreteDecoratorA d1 = new ConcreteDecoratorA();
		ConcreteDecoratorB d2 = new ConcreteDecoratorB();
		d1.setComponent(component);
		d2.setComponent(d1);
		d2.request();
	}
}

package _designPatterns.step07.decorator;

public class ConcreteDecoratorB extends Decorator {

	//装饰: 增加额外的方法
	public void addBehavior() {
		System.out.println("ConcreteDecoratorB do something");
	}
	
	@Override
	public void request() {
		addBehavior();
		super.request();
	}
}

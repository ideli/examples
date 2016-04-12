package _designPatterns.step07.decorator;

public class ConcreteDecoratorA extends Decorator {

	//装饰: 增加额外的属性
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void request() {
		System.out.println("ConcreteDecoratorA do something");
		super.request();
	}
}

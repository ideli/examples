package _designPatterns.step02.factoryMethod;

public class OrangeFactory implements Factory {

	@Override
	public Fruit createFruit() {
		return new Orange();
	}

}

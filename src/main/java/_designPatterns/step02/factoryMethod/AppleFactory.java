package _designPatterns.step02.factoryMethod;

public class AppleFactory implements Factory {

	@Override
	public Fruit createFruit() {
		return new Apple();
	}

}

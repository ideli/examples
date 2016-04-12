package _designPatterns.step02.factoryMethod;

public class Apple extends Fruit {

	@Override
	public String getName() {
		return this.getClass().getTypeName();
	}

}

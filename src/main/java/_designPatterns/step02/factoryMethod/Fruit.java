package _designPatterns.step02.factoryMethod;

public abstract class Fruit {

	public void show() {
		System.out.println("I'm " + getName());
	}
	
	public abstract String getName();
}

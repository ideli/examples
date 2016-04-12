package _designPatterns.step03.abstractFactory;

public abstract class Car {

	public void show() {
		System.out.println("I'm " + getProduct() + " car");
	}
	
	public abstract String getProduct();
}

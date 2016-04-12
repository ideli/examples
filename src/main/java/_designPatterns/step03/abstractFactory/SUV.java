package _designPatterns.step03.abstractFactory;

public abstract class SUV {

	public void show() {
		System.out.println("I'm " + getProduct() + " SUV");
	}
	
	public abstract String getProduct();
}

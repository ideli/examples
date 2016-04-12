package _designPatterns.step14.templateMethod;

public abstract class Animal {

	public void show() {
		System.out.println(getName() + "喜欢的食物是" + getFood());
	}
	
	public abstract String getName();
	public abstract String getFood();
}

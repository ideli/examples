package _designPatterns.step14.templateMethod;

public class Dog extends Animal {

	@Override
	public String getName() {
		return "狗";
	}

	@Override
	public String getFood() {
		return "肉";
	}

}

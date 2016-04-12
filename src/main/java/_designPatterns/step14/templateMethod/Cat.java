package _designPatterns.step14.templateMethod;

public class Cat extends Animal {

	@Override
	public String getName() {
		return "猫";
	}

	@Override
	public String getFood() {
		return "鱼";
	}

}

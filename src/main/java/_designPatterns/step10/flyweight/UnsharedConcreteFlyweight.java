package _designPatterns.step10.flyweight;

public class UnsharedConcreteFlyweight extends Flyweight {

	@Override
	public void operation(int extrinsicstate) {
		System.out.println("不共享Flyweight: " + extrinsicstate);
	}

}

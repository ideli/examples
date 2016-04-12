package _designPatterns.step10.flyweight;

public class ConcreteFlyweight extends Flyweight {

	@Override
	public void operation(int extrinsicstate) {
		System.out.println("共享Flyweight: " + extrinsicstate);
	}

}

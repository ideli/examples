package _designPatterns.step10.flyweight;

/*
 * 享元模式
 */
public class Client {

	public void test() {
		int extrinsicstate = 20;
		
		FlyweightFactory factory = new FlyweightFactory();
		
		Flyweight fx = factory.getFlyweight("X");
		fx.operation(--extrinsicstate);
		
		Flyweight fy = factory.getFlyweight("Y");
		fy.operation(--extrinsicstate);
		
		Flyweight fz = factory.getFlyweight("Z");
		fz.operation(--extrinsicstate);
		
		Flyweight uf = new UnsharedConcreteFlyweight();
		uf.operation(--extrinsicstate);
	}
}

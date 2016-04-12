package _designPatterns.step02.factoryMethod;

/*
 * 工厂方法模式
 * 一种"产品"对应一个工厂类,增加新"产品"需要增加新工厂类,符合开闭原则
 * 在本例中,每增加一种水果需要增加一个新的水果工厂类(不在工厂类中做分支判断)
 */
public class Client {

	public void test() {
		Fruit f1 = new AppleFactory().createFruit();
		f1.show();
		Fruit f2 = new OrangeFactory().createFruit();
		f2.show();
	}
}

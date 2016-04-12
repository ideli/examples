package _designPatterns.step03.abstractFactory;

/*
 * 抽象工厂模式
 * 为创建一组相关或者互相依赖的对象提供一个接口,而无需指定它们对应的具体类
 * 与工厂方法区别,工厂方法一个工厂类对应一种产品,抽象工厂一个工厂类对应一个系列的产品
 * 抽象工厂模式可以方便的增加"生产家族",但是增加商品时不符合开闭原则
 */
public class Client {

	public void test() {
		Factory f1 = new AudiFactory();
		f1.createCar().show();
		f1.createSUV().show();
		
		Factory f2 = new BenzFactory();
		f2.createCar().show();
		f2.createSUV().show();
	}
}

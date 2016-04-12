package _designPatterns.step21.strategy;

/*
 * 策略模式
 */
public class Client {

	public void test() {
		//使用哪一种算法在模式外决定
		Strategy strategy = new AdvancedMemberStrategy();
		Context context = new Context(strategy);
		double price = context.calcPrice(300);
		System.out.println("图书的最终价格为: " + price);
	}
}

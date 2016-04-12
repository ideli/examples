package _designPatterns.step21.strategy;

public class AdvancedMemberStrategy implements Strategy {

	@Override
	public double calcPrice(double booksPrice) {
		System.out.println("中级会员的折扣为20%");
		return booksPrice * 0.8;
	}

}

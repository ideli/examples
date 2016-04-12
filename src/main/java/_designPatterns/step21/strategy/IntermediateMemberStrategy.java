package _designPatterns.step21.strategy;

public class IntermediateMemberStrategy implements Strategy {

	@Override
	public double calcPrice(double booksPrice) {
		System.out.println("中级会员的折扣为10%");
		return booksPrice * 0.9;
	}

}

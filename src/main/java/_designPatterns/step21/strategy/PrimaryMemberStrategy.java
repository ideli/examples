package _designPatterns.step21.strategy;

public class PrimaryMemberStrategy implements Strategy {

	@Override
	public double calcPrice(double booksPrice) {
		System.out.println("对初级会员没有折扣");
		return booksPrice;
	}

}

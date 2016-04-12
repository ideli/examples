package _designPatterns.step21.strategy;

public class Context {

	private Strategy strategy;
	
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public double calcPrice(double booksPrice) {
		return this.strategy.calcPrice(booksPrice);
	}
}

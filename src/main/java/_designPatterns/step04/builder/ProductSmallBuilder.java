package _designPatterns.step04.builder;

public class ProductSmallBuilder extends Builder {
	
	private Product f = new ProductSmall();

	@Override
	public Product getResult() {
		return f;
	}

	@Override
	public void buildPartA() {
		f.addPart("Small-Part-A");
	}

	@Override
	public void buildPartB() {
		f.addPart("Small-Part-B");
	}

	@Override
	public void buildPartC() {
		f.addPart("Small-Part-C");
	}
}

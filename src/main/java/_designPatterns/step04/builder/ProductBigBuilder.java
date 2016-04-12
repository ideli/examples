package _designPatterns.step04.builder;

public class ProductBigBuilder extends Builder {

	private Product f = new ProductBig();
	
	@Override
	public Product getResult() {
		return f;
	}

	@Override
	public void buildPartA() {
		f.addPart("Big-Part-A");
	}

	@Override
	public void buildPartB() {
		f.addPart("Big-Part-B");
	}

	@Override
	public void buildPartC() {
		f.addPart("Big-Part-C");
	}
}

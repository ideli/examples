package _designPatterns.step04.builder;

public class Director {

	private Builder builder;
	
	public Director(Builder builder) {
		this.builder = builder;
	}
	
	public void createProduct() {
		//建造步骤必须一致
		builder.buildPartA();
		builder.buildPartB();
		builder.buildPartC();
	}
}

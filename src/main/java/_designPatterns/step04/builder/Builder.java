package _designPatterns.step04.builder;

public abstract class Builder {

	public abstract Product getResult();
	
	public abstract void buildPartA();
	
	public abstract void buildPartB();
	
	public abstract void buildPartC();
}

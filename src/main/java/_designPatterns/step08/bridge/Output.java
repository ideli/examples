package _designPatterns.step08.bridge;

public abstract class Output {

	protected Writer writer;
	
	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public abstract void save();
}

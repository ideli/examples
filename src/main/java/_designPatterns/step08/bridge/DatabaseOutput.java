package _designPatterns.step08.bridge;

public class DatabaseOutput extends Output {

	@Override
	public void save() {
		writer.write();
	}

}

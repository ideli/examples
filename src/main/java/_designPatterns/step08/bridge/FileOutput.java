package _designPatterns.step08.bridge;

public class FileOutput extends Output {

	@Override
	public void save() {
		writer.write();
	}

}

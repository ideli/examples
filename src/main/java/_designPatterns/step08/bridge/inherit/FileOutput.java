package _designPatterns.step08.bridge.inherit;

public class FileOutput extends Output {

	@Override
	public void save() {
		System.out.println("使用文件存储");
	}

}

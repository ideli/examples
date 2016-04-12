package _designPatterns.step08.bridge.inherit;

public class BufferedFileOutput extends FileOutput {

	@Override
	public void save() {
		System.out.println("以缓存方式使用文件存储");
	}
}

package _designPatterns.step08.bridge.inherit;

public class RemoteFileOutput extends FileOutput {

	@Override
	public void save() {
		System.out.println("以远程方式使用文件存储");
	}
}

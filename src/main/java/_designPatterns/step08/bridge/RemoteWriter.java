package _designPatterns.step08.bridge;

public class RemoteWriter extends Writer {

	@Override
	public void write() {
		System.out.println("以远程方式");
	}

}

package _designPatterns.step08.bridge;

public class BufferedWriter extends Writer {

	@Override
	public void write() {
		System.out.println("以缓存方式");
		
	}

}

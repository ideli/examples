package _designPatterns.step08.bridge.inherit;

public class BufferedDatabaseOutput extends DatabaseOutput {

	@Override
	public void save() {
		System.out.println("以缓存方式使用数据库存储");
	}
}

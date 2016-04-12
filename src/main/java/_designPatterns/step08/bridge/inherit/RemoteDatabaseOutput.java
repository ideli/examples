package _designPatterns.step08.bridge.inherit;

public class RemoteDatabaseOutput extends DatabaseOutput {

	@Override
	public void save() {
		System.out.println("以远程方式使用数据库存储");
	}
}

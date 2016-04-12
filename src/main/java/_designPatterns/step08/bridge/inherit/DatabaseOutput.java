package _designPatterns.step08.bridge.inherit;

public class DatabaseOutput extends Output {

	@Override
	public void save() {
		System.out.println("使用数据库存储");
	}

}

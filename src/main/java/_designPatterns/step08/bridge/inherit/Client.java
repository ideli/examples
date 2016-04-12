package _designPatterns.step08.bridge.inherit;

/*
 * 使用传统继承方式实现
 * 如果增加第三种写入方式则需要在每种存储介质下加一个类
 */
public class Client {

	public void test() {
		Output op = new BufferedFileOutput();
		op.save();
		op = new RemoteFileOutput();
		op.save();
		op = new BufferedDatabaseOutput();
		op.save();
		op = new RemoteDatabaseOutput();
		op.save();
	}
}

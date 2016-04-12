package _designPatterns.step08.bridge;

/*
 * 桥接模式
 */
public class Client {

	public void test() {
		Writer w1 = new BufferedWriter();
		Writer w2 = new RemoteWriter();
		Output op = new FileOutput();
		op.setWriter(w1);
		op.save();
		
		op.setWriter(w2);
		op.save();
		
		op = new DatabaseOutput();
		op.setWriter(w1);
		op.save();
		
		op.setWriter(w2);
		op.save();
	}
}

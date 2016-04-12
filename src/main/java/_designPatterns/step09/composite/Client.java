package _designPatterns.step09.composite;

/*
 * 组合模式
 */
public class Client {

	public void test() {
		MenuComposite root = new MenuComposite("root");
		root.add(new MenuLeaf("leaf a"));
		root.add(new MenuLeaf("leaf b"));
		
		MenuComposite level1 = new MenuComposite("level1 composite a");
		MenuComposite level2 = new MenuComposite("level2 composite a");
		level1.add(level2);
		root.add(level1);
		root.display();
	}
}

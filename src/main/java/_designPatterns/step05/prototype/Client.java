package _designPatterns.step05.prototype;

/*
 * 原型模式
 */
public class Client {

	public void test() {
		ShallowCopy obj1 = new ShallowCopy("obj1", "test1");
		ShallowCopy obj2 = obj1.clone();
		System.out.println(obj1 == obj2);
		System.out.println(obj1.getList() == obj2.getList());
		
		DeepCopy obj3 = new DeepCopy("obj3", "test3");
		DeepCopy obj4 = obj3.clone();
		System.out.println(obj3 == obj4);
		System.out.println(obj3.getList() == obj4.getList());
	}
}

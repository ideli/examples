package _designPatterns.step14.templateMethod;

/*
 * 模板方法模式
 */
public class Client {

	public void test() {
		Animal cat = new Cat();
		cat.show();
		Animal dog = new Dog();
		dog.show();
	}
}

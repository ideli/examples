package _designPatterns.step20.visitor;

/*
 * 访问者模式
 */
public class Client {

	public void test() {
		ObjectStructure o = new ObjectStructure();
		o.attach(new Man());
		o.attach(new Woman());
		
		SuccessAction a1 = new SuccessAction();
		o.display(a1);
		
		FailingAction a2 = new FailingAction();
		o.display(a2);
		
		AmativenessAction a3 = new AmativenessAction();
		o.display(a3);
	}
}

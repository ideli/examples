package _designPatterns.step13.observer;

/*
 * 观察者模式
 */
public class Client {

	public void test() {
		Observer o1 = new ConcreteObserver("观察者1");
		Observer o2 = new ConcreteObserver("观察者2");
		Observer o3 = new ConcreteObserver("观察者3");
		
		ConcreteSubject s = new ConcreteSubject();
		s.attach(o1);
		s.attach(o2);
		s.attach(o3);
		s.detach(o3);
		s.change("睡醒了");
	}
}

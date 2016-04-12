package _designPatterns.step19.mediator;

/*
 * 中介者模式
 */
public class Client {

	public void test() {
		ConcreteMediator m = new ConcreteMediator();
		
		ConcreteColleagueA ca = new ConcreteColleagueA(m);
		ConcreteColleagueB cb = new ConcreteColleagueB(m);
		m.setConcreteColleagueA(ca);
		m.setConcreteColleagueB(cb);
		
		ca.say("吃过饭了吗?");
		cb.say("没有呢,一起去?");
	}
}

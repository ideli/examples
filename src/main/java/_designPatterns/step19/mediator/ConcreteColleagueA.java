package _designPatterns.step19.mediator;

public class ConcreteColleagueA extends Colleague {

	public ConcreteColleagueA(Mediator mediator) {
		super(mediator);
	}

	public void say(String message) {
		System.out.println("同事A说: " + message);
		this.mediator.send(message, this);
	}
	
	public void receive(String message) {
		System.out.println("同事A收到: " + message);
	}
}

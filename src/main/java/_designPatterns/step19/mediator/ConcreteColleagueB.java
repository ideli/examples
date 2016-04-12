package _designPatterns.step19.mediator;

public class ConcreteColleagueB extends Colleague {

	public ConcreteColleagueB(Mediator mediator) {
		super(mediator);
	}

	public void say(String message) {
		System.out.println("同事B说: " + message);
		this.mediator.send(message, this);
	}
	
	public void receive(String message) {
		System.out.println("同事B收到: " + message);
	}
}

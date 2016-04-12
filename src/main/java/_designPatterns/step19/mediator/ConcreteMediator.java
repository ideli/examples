package _designPatterns.step19.mediator;

public class ConcreteMediator extends Mediator {

	private ConcreteColleagueA concreteColleagueA;
	private ConcreteColleagueB concreteColleagueB;
	
	public void setConcreteColleagueA(ConcreteColleagueA concreteColleagueA) {
		this.concreteColleagueA = concreteColleagueA;
	}

	public void setConcreteColleagueB(ConcreteColleagueB concreteColleagueB) {
		this.concreteColleagueB = concreteColleagueB;
	}

	@Override
	public void send(String message, Colleague colleague) {
		if(colleague == concreteColleagueA) {
			concreteColleagueB.receive(message);
		} else {
			concreteColleagueA.receive(message);
		}
	}

}

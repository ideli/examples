package _designPatterns.step16.state;

public class ConcreteStateB implements State {

	@Override
	public void handle(String someCondition) {
		System.out.println("ConcreteStateB handle: " + someCondition);
	}

}

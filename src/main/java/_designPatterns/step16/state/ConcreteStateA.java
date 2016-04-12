package _designPatterns.step16.state;

public class ConcreteStateA implements State {

	@Override
	public void handle(String someCondition) {
		System.out.println("ConcreteStateA handle: " + someCondition);
	}

}

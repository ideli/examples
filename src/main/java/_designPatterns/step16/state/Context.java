package _designPatterns.step16.state;

public class Context {

	private State state;

	public void setState(State state) {
		this.state = state;
	}
	
	public void request(String someCondition) {
		this.state.handle(someCondition);
	}
}

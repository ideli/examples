package _designPatterns.step13.observer;

public class ConcreteSubject extends Subject {

	private String state;

	
	public String getState() {
		return state;
	}

	public void change(String newState) {
		this.state = newState;
		super.notifyObservers(this.state);
	}
}

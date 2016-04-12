package _designPatterns.step20.visitor;

public class Woman extends Person {

	@Override
	public void accept(Action action) {
		action.getWomanConclusion();
	}
}

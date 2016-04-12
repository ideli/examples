package _designPatterns.step20.visitor;

public class Man extends Person {

	@Override
	public void accept(Action action) {
		action.getManConclusion();
	}
}

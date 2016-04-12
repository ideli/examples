package _designPatterns.step20.visitor;

public class AmativenessAction extends Action {

	@Override
	public void getManConclusion() {
		System.out.println("男人恋爱的论断");
	}

	@Override
	public void getWomanConclusion() {
		System.out.println("女人恋爱的论断");
	}
}

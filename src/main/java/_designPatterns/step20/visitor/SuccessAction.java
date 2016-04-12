package _designPatterns.step20.visitor;

public class SuccessAction extends Action {

	@Override
	public void getManConclusion() {
		System.out.println("男人成功的论断");
	}

	@Override
	public void getWomanConclusion() {
		System.out.println("女人成功的论断");
	}

}

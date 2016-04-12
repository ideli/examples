package _designPatterns.step20.visitor;

public class FailingAction extends Action {

	@Override
	public void getManConclusion() {
		System.out.println("男人失败的论断");
	}

	@Override
	public void getWomanConclusion() {
		System.out.println("女人失败的论断");
	}

}

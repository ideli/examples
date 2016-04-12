package _designPatterns.step17.chainofresponsibility;

/*
 * 总经理
 */
public class GeneralManager extends Manager {

	public GeneralManager(String name) {
		super(name);
	}

	@Override
	public void handleRequest(Request request) {
		if(request.getType().equals("请假")) {
			System.out.println(name + ": " + request.getType() + "(" + request.getNumber() + ")被批准");
		}
	}
}

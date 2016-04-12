package _designPatterns.step17.chainofresponsibility;

/*
 * 经理
 */
public class CommonManager extends Manager {

	public CommonManager(String name) {
		super(name);
	}

	@Override
	public void handleRequest(Request request) {
		if(request.getType().equals("请假") && request.getNumber() <= 2) {
			System.out.println(name + ": " + request.getType() + "(" + request.getNumber() + ")被批准");
		} else {
			System.out.println(name + ": " + request.getType() + "(" + request.getNumber() + ")已转给" + superior.getName());
			superior.handleRequest(request);
		}
		
	}

}

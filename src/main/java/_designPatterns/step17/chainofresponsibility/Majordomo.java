package _designPatterns.step17.chainofresponsibility;

/*
 * 总监
 */
public class Majordomo extends Manager {

	public Majordomo(String name) {
		super(name);
	}

	@Override
	public void handleRequest(Request request) {
		if(request.getType().equals("请假") && request.getNumber() <= 5) {
			System.out.println(name + ": " + request.getType() + "(" + request.getNumber() + ")被批准");
		} else {
			System.out.println(name + ": " + request.getType() + "(" + request.getNumber() + ")已转给" + superior.getName());
			superior.handleRequest(request);
		}
	}

}

package _designPatterns.step17.chainofresponsibility;

/*
 * 职责链模式
 */
public class Client {

	public void test() {
		CommonManager m1 = new CommonManager("经理");
		Majordomo m2 = new Majordomo("总监");
		GeneralManager m3 = new GeneralManager("总经理");
		
		m1.setSuperior(m2);
		m2.setSuperior(m3);
		
		Request r1 = new Request("请假", 2);
		Request r2 = new Request("请假", 5);
		Request r3 = new Request("请假", 8);
		
		m1.handleRequest(r1);
		m1.handleRequest(r2);
		m1.handleRequest(r3);
	}
}

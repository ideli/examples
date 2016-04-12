package _designPatterns.step17.chainofresponsibility;

/*
 * 申请
 */
public class Request {

	public Request(String type, int number) {
		this.type = type;
		this.number = number;
	}
	
	private String type;
	private int number;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
}

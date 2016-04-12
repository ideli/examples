package _designPatterns.step12.facade;

public class Facade {

	SubSystemOne one;
	SubSystemTwo two;
	SubSystemThree three;
	SubSystemFour four;
	
	public Facade() {
		one = new SubSystemOne();
		two = new SubSystemTwo();
		three = new SubSystemThree();
		four = new SubSystemFour();
	}
	
	public void businessA() {
		System.out.println("业务A由以下方法组成=====================");
		one.MethodOne();
		two.MethodTwo();
		four.MethodFour();
		System.out.println("");
	}
	
	public void businessB() {
		System.out.println("业务B由以下方法组成=====================");
		two.MethodTwo();
		three.MethodThree();
		four.MethodFour();
		System.out.println("");
	}
}

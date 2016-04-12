package _aop.step09.autoProxyCreator;

public class Waiter {

	public void greetTo(String name) {
		System.out.println("Waiter greet to " + name + "...");
	}
	
	public void serveTo(String name) {
		System.out.println("Waiter serving to " + name + "...");
	}
}

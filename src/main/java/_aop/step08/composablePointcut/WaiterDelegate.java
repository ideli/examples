package _aop.step08.composablePointcut;

public class WaiterDelegate {

	private Waiter waiter;
	public void service(String clientName) {
		waiter.greetTo(clientName);
		waiter.serveTo(clientName);
	}
	
	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}
}

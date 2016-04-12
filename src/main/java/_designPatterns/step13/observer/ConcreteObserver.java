package _designPatterns.step13.observer;

public class ConcreteObserver extends Observer {

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ConcreteObserver(String name) {
		this.name = name;
	}

	@Override
	public void update(String state) {
		System.out.println(name + "收到通知, 通知者状态更新为: " + state);
	}

}

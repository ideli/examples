package _designPatterns.step16.state;

/*
 * 状态模式
 */
public class Client {

	public void test() {
		Context context = new Context();
		State state = new ConcreteStateB();
		context.setState(state);
		context.request("test");
	}
}

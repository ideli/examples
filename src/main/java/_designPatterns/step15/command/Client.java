package _designPatterns.step15.command;

/*
 * 命令模式
 * 设计命令队列/将命令记入日志/允许接收方否决请求/实现请求的撤销和重做
 */
public class Client {

	public void test() {
		Cook cook = new Cook("张师傅");
		
		Command cmd1 = new MakeHamburger(cook);
		Command cmd2 = new MakeHamburger(cook);
		Command cmd3 = new MakeChicken(cook);
		Command cmd4 = new MakeChicken(cook);
		//下单
		Waiter waiter = new Waiter();
		waiter.setCommand(cmd1);
		waiter.setCommand(cmd2);
		waiter.setCommand(cmd3);
		//撤销
		waiter.cancelCommand(cmd2);
		waiter.setCommand(cmd4);
		
		
		waiter.notifyCook();
	}
}

package _designPatterns.step15.command;

import java.util.ArrayList;
import java.util.List;

public class Waiter {

	private List<Command> list = new ArrayList<>();
	
	public void setCommand(Command cmd) {
		if(list.size() >= 2) {
			System.out.println("暂时接不了单了");
		} else {
			System.out.println("接收订单");
			list.add(cmd);
		}
	}
	
	public void cancelCommand(Command cmd) {
		list.remove(cmd);
	}
	
	public void notifyCook() {
		for(Command cmd : list) {
			cmd.execute();
		}
	}
}

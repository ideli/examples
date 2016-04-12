package _designPatterns.step15.command;

/*
 * 厨师(命令执行者)
 */
public class Cook {

	private String name;
	
	public Cook(String name) {
		this.name = name;
	}
	
	public void makeHamburger() {
		System.out.println(name + "开始制作汉堡");
	}
	
	public void makeChicken() {
		System.out.println(name + "开始制作炸鸡");
	}
}

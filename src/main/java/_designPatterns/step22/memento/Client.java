package _designPatterns.step22.memento;

/*
 * 备忘录模式
 */
public class Client {

	public void test() {
		Originator originator = new Originator(100, 5000);
		originator.show();
		
		System.out.println("保存当前状态");
		Caretaker taker = new Caretaker();
		taker.setMemento(originator.save());
		
		System.out.println();
		System.out.println("战斗后");
		originator.setVitality(50);
		originator.setAttack(1000);
		originator.show();
		
		System.out.println();
		System.out.println("恢复战斗前状态");
		originator.recovery(taker.getMemento());
		originator.show();
	}
}

package _enum.step03;

import java.util.EnumSet;

public class Client {

	public void test() {
		EnumSet<Soldier> soldiers = EnumSet.of(Soldier.Swordman, Soldier.Horseman);
		for(Soldier s : soldiers) {
			s.fight();
		}
	}
}

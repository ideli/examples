package _designPatterns.step22.memento;

/*
 * 备忘录
 */
public class Memento {

	public Memento(int vitality, int attack) {
		this.vitality = vitality;
		this.attack = attack;
	}
	
	private int vitality;
	private int attack;
	
	public int getVitality() {
		return vitality;
	}
	public int getAttack() {
		return attack;
	}
	
	
}

package _designPatterns.step22.memento;

/*
 * 发起人
 */
public class Originator {

	public Originator(int vitality, int attack) {
		this.vitality = vitality;
		this.attack = attack;
	}
	
	//需要保存的状态
	private int vitality;
	private int attack;
	
	public int getVitality() {
		return vitality;
	}
	public void setVitality(int vitality) {
		this.vitality = vitality;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public Memento save() {
		return new Memento(this.vitality, this.attack);
	}
	
	public void recovery(Memento memento) {
		this.vitality = memento.getVitality();
		this.attack = memento.getAttack();
	}
	
	public void show() {
		System.out.println("生命力: " + this.vitality + ",攻击力: " + this.attack);
	}
}

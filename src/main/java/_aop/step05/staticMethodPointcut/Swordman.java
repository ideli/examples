package _aop.step05.staticMethodPointcut;

public class Swordman {
	public void block(String enemy) {
		System.out.println(this.getClass().getSimpleName() + "格挡" + enemy);
	}
	
	public void chop(String enemy) {
		System.out.println(this.getClass().getSimpleName() + "砍劈攻击" + enemy);
	}
}

package _enum.step02;

public class Client {

	public void test() {
		for(int i = 0; i < 10; i++) {
			System.out.println(Enums.random(Animal.class).getDescription());
		}
	}
}

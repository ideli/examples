package _designPatterns.step11.proxy;

public class RealSubject extends Subject {

	@Override
	public void request() {
		System.out.println("RealSubject request");
	}

}

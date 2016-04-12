package _designPatterns.step11.proxy;

public class Proxy extends Subject {

	private Subject subject;
	
	@Override
	public void request() {
		if(subject == null)
			subject = new RealSubject();
		subject.request();
	}

}

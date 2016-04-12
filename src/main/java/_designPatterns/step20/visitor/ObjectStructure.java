package _designPatterns.step20.visitor;

import java.util.ArrayList;
import java.util.List;

public class ObjectStructure {

	public List<Person> list = new ArrayList<>();
	
	public void attach(Person p) {
		list.add(p);
	}
	
	public void detach(Person p) {
		list.remove(p);
	}
	
	public void display(Action visitor) {
		for(Person p : list) {
			p.accept(visitor);
		}
	}
}

package _designPatterns.step13.observer;

import java.util.List;
import java.util.ArrayList;

public abstract class Subject {

	protected List<Observer> list = new ArrayList<>();
	
	public void attach(Observer observer) {
		this.list.add(observer);
	}
	
	public void detach(Observer observer) {
		this.list.remove(observer);
	}
	
	public void notifyObservers(String newState) {
		for(Observer o : list) {
			o.update(newState);
		}
	}
}

package _designPatterns.step09.composite;

import java.util.ArrayList;
import java.util.List;

public class MenuComposite extends MenuComponent {

	public MenuComposite(String name) {
		super(name);
	}

	private List<MenuComponent> list = new ArrayList<>();
	
	@Override
	public void add(MenuComponent component) {
		this.list.add(component);
	}

	@Override
	public void remove(MenuComponent component) {
		this.list.remove(component);
	}

	@Override
	public void display() {
		System.out.println("MenuComposite: " + name);
		for(MenuComponent child : list) {
			child.display();
		}
	}

}

package _designPatterns.step09.composite;

public abstract class MenuComponent {
	
	protected String name;
	
	public MenuComponent(String name) {
		this.name = name;
	}

	public abstract void add(MenuComponent component);
	
	public abstract void remove(MenuComponent component);
	
	public abstract void display();
}

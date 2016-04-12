package _designPatterns.step09.composite;

public class MenuLeaf extends MenuComponent {

	public MenuLeaf(String name) {
		super(name);
	}

	@Override
	public void add(MenuComponent component) {
		System.out.println("Can't add to a leaf");
	}

	@Override
	public void remove(MenuComponent component) {
		System.out.println("Can't remove from a leaf");
	}

	@Override
	public void display() {
		System.out.println("MenuLeaf: " + name);
	}

}

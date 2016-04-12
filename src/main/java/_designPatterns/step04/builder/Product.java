package _designPatterns.step04.builder;

import java.util.ArrayList;
import java.util.List;

public abstract class Product {

	protected List<String> list = new ArrayList<>();
	
	public void addPart(String part) {
		list.add(part);
	}
	
	public void show() {
		String desc = getName() + ": ";
		for(String s : list) {
			desc += s + " ";
		}
		System.out.println(desc);
	}
	
	public abstract String getName();
}

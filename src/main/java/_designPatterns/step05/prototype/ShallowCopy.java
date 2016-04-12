package _designPatterns.step05.prototype;

import java.util.ArrayList;
import java.util.List;

public class ShallowCopy implements Cloneable {

	public ShallowCopy(String name, String value) {
		this.name = name;
		list.add(value);
	}
	
	private String name;
	private List<String> list = new ArrayList<>();
	
	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ShallowCopy clone() {
		try {
			return (ShallowCopy)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}

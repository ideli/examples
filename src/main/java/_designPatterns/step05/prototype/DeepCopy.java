package _designPatterns.step05.prototype;

import java.util.ArrayList;
import java.util.List;

public class DeepCopy implements Cloneable {

	public DeepCopy(String name, String value) {
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

	@SuppressWarnings("unchecked")
	public DeepCopy clone() {
		DeepCopy obj = null;
		try {
			obj = (DeepCopy)super.clone();
			obj.list = (ArrayList<String>)((ArrayList<String>)list).clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}

package _spring.step09.jdbc;

public class Car {

	public Car() {}
	
	public Car(int id, String name, float price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	private int id;
	private String name;
	private float price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
}

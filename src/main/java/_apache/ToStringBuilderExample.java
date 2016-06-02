package _apache;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ToStringBuilderExample {

	public static void main(String[] args) {
		ToStringBuilder.reflectionToString(new Car(1, "audi"), ToStringStyle.JSON_STYLE);
	}
}

class Car {
	
	public Car(int id, String brand) {
		super();
		this.id = id;
		this.brand = brand;
	}
	private int id;
	private String brand;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
}

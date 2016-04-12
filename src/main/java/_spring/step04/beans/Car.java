package _spring.step04.beans;

public class Car {

	private String brand;
	private String color;
	private int maxSpeed;
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	private void init() {
		System.out.println("init");
	}
	
	private void destroy() {
		System.out.println("destroy");
	}
}

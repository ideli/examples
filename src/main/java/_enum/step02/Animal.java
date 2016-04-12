package _enum.step02;

public enum Animal {

	DOG("dog"),
	CAT("cat");
	
	private Animal(String description) {
		this.description = description;
	}
	
	private String description;
	
	public String getDescription() {
		return this.description;
	}
}

package _designPatterns.step03.abstractFactory;

public class AudiFactory extends Factory {

	@Override
	public Car createCar() {
		return new AudiCar();
	}

	@Override
	public SUV createSUV() {
		return new AudiSUV();
	}

}

package _designPatterns.step03.abstractFactory;

public class BenzFactory extends Factory {

	@Override
	public Car createCar() {
		return new BenzCar();
	}

	@Override
	public SUV createSUV() {
		return new BenzSUV();
	}
}

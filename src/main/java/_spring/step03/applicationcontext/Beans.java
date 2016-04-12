package _spring.step03.applicationcontext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import _spring.step02.beanfactory.Car;

@Configuration
public class Beans {

	@Bean(name = "car3")
	public Car buildCar() {
		Car car = new Car();
		car.setBrand("benz");
		car.setColor("black");
		car.setMaxSpeed(240);
		return car;
	}
}

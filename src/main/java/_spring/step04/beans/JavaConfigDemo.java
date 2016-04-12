package _spring.step04.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * 基于Java类配置
 */
@Configuration
public class JavaConfigDemo {

	@Bean
	public Car car(){
		return new Car();
	}
}

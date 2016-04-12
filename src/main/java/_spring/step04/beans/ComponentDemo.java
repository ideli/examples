package _spring.step04.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("componentDemo")
public class ComponentDemo {

	//等同于<bean id="componentDemo" class="_spring.step04.beans.ComponentDemo" />
	//其他类似注解 @Repository @Service @Controller
	
	@PostConstruct //init-method
	private void init1() {
		System.out.println("init1");
	}
	
	@PostConstruct
	private void init2() {
		System.out.println("init2");
	}
	
	@PreDestroy //destroy-method
	private void destroy1() {
		System.out.println("destroy1");
	}
	
	private void destroy2() {
		System.out.println("destroy2");
	}
}

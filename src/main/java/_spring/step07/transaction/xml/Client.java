package _spring.step07.transaction.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_spring/step07/transaction/xml/applicationContext.xml");
		UserService userService = (UserService)context.getBean("userService");
		userService.addUser("jack");
	}
}

package _spring.step08.transaction.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DuplicateKeyException;

public class Client {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_spring/step08/transaction/annotation/applicationContext.xml");
		UserService userService = (UserService)context.getBean("userService");
		try {
			userService.addUser("mike");
		} catch (DuplicateKeyException ex) {
			System.out.println("插入失败");
		}
		System.out.println(userService.getUserName(1));
	}
}

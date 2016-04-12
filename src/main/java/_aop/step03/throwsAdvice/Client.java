package _aop.step03.throwsAdvice;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_aop/step03/throwsAdvice/beans.xml");
		UserService userService = (UserService)context.getBean("userService");
		try {
			userService.removeUser(0);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		try {
			userService.updateUser(0, "Jone");
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}
}

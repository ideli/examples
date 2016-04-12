package _aop.step04.introduction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_aop/step04/introduction/beans.xml");

		UserService userService1 = (UserService)context.getBean("userService1");
		userService1.removeUser(0);
		userService1.updateUser(0, "Jone");
		System.out.println("开启监控");
		Monitorable monitorable1 = (Monitorable)userService1;
		monitorable1.setMonitorActive(true);
		userService1.removeUser(0);
		userService1.updateUser(0, "Jone");
		System.out.println("--------------------------------------------");
		
		UserService userService2 = (UserService)context.getBean("userService2");
		userService2.removeUser(0);
		userService2.updateUser(0, "Jone");
		System.out.println("开启监控");
		Monitorable monitorable2 = (Monitorable)userService2;
		monitorable2.setMonitorActive(true);
		userService2.removeUser(0);
		userService2.updateUser(0, "Jone");
	}
}

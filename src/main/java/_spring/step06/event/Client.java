package _spring.step06.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_spring/step06/event/beans.xml");
		MailSender sender = context.getBean("mailSender", MailSender.class);
		sender.sendMail("demo@test.com");
	}
}

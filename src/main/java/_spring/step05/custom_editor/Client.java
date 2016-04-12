package _spring.step05.custom_editor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_spring/step05/custom_editor/beans.xml");
		Boss boss = ctx.getBean("boss", Boss.class);
		System.out.println(boss.getCar().getBrand());
	}
}

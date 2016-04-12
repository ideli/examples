package _aop.step05.staticMethodPointcut;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_aop/step05/staticMethodPointcut/beans.xml");
		Waiter waiter = (Waiter)context.getBean("waiter2");
		waiter.greetTo("John");
		waiter.serveTo("Tom");
	}
}

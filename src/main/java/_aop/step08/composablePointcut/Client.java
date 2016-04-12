package _aop.step08.composablePointcut;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_aop/step08/composablePointcut/beans.xml");
		Waiter waiter = (Waiter)context.getBean("waiter2");
		WaiterDelegate wd = new WaiterDelegate();
		wd.setWaiter(waiter);
		waiter.greetTo("John");
		waiter.serveTo("Tom");
		wd.service("Peter");
	}
}

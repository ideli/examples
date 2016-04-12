package _aop.step09.autoProxyCreator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
	
	public static void main(String[] args) {
		ApplicationContext context1 = new ClassPathXmlApplicationContext("_aop/step09/autoProxyCreator/beans-BeanName.xml");
		Waiter waiter1 = (Waiter)context1.getBean("waiter1");
		waiter1.greetTo("John");
		waiter1.serveTo("Tom");
		
		Seller seller1 = (Seller)context1.getBean("seller1");
		seller1.greetTo("Waiter");
		
		ApplicationContext context2 = new ClassPathXmlApplicationContext("_aop/step09/autoProxyCreator/beans-DefaultAdvisor.xml");
		Waiter waiter2 = (Waiter)context2.getBean("waiter2");
		waiter2.greetTo("John");
		waiter2.serveTo("Tom");
		
		Seller seller2 = (Seller)context2.getBean("seller2");
		seller2.greetTo("Waiter");
	}
}

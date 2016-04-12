package _aop.step02.methodAdvice;

import java.sql.SQLException;

import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
	
	public static void main(String[] args) {
		//使用代码方式添加增强
		addAdviceWithCode();
		System.out.println("--------------------------------------------");
		//使用配置添加增强
		addAdviceWithXml();
	}

	public static void addAdviceWithCode() {
		Waiter target = new NaiveWaiter();
		BeforeAdvice advice = new GreetingBeforeAdvice();
		//Spring提供的代理工厂
		ProxyFactory pf = new ProxyFactory();
		//设置代理目标
		pf.setTarget(target);
		//如果是setInterfaces则使用JDK代理技术
		pf.setInterfaces(target.getClass().getInterfaces());
		//为代理目标设置增强
		pf.addAdvice(advice);
		//启动优化(使用Cglib)
		pf.setOptimize(true);
		//生成代理实例
		Waiter proxy = (Waiter)pf.getProxy();
		proxy.greetTo("John");
		proxy.serveTo("Tom");
	}
	
	public static void addAdviceWithXml() {
		ApplicationContext context = new ClassPathXmlApplicationContext("_aop/step02/methodAdvice/beans.xml");
		Waiter waiter1 = (Waiter)context.getBean("waiter1");
		waiter1.greetTo("John");
		waiter1.serveTo("Tom");
		
		System.out.println("--------------------------------------------");
		
		Waiter waiter2 = (Waiter)context.getBean("waiter2");
		waiter2.greetTo("Zhang");
		waiter2.serveTo("Lee");
		
		System.out.println("--------------------------------------------");
	}
}

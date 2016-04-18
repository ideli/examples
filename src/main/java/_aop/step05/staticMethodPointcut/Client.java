package _aop.step05.staticMethodPointcut;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_aop/step05/staticMethodPointcut/applicationContext.xml");
		Horseman hm = (Horseman)context.getBean("horseman");
		Swordman sm = (Swordman)context.getBean("swordman");
		hm.rush("Ghoul");
		hm.chop("Ghoul");
		sm.block("Ghoul");
		sm.chop("Ghoul");
		System.out.println("----------------------");
		Horseman hm1 = (Horseman)context.getBean("horseman1");
		Swordman sm1 = (Swordman)context.getBean("swordman1");
		hm1.rush("Ghoul");
		hm1.chop("Ghoul");
		sm1.block("Ghoul");
		sm1.chop("Ghoul");
	}

}

package _aop.step06.dynamicMethodPointcut;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import _aop.step05.staticMethodPointcut.Horseman;
import _aop.step05.staticMethodPointcut.Swordman;

public class Client {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_aop/step06/dynamicMethodPointcut/applicationContext.xml");
		Horseman hm = (Horseman)context.getBean("horseman");
		Swordman sm = (Swordman)context.getBean("swordman");
		hm.rush("Ghoul");
		hm.chop("Ghoul");
		sm.block("Ghoul");
		sm.chop("Ghoul");
		hm.rush("Abomination");
		hm.chop("Abomination");
		sm.block("Abomination");
		sm.chop("Abomination");
	}
}

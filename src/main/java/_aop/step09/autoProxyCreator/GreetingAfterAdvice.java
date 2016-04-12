package _aop.step09.autoProxyCreator;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

/**
 * 在方法后织入增强
 */
public class GreetingAfterAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnObj, Method method, Object[] args,
			Object obj) throws Throwable {
		System.out.println("[AfterReturningAdvice]Please enjoy yourself!");
	}

}

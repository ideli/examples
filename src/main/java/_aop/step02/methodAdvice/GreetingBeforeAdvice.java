package _aop.step02.methodAdvice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * 在方法前织入增强
 */
public class GreetingBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object obj)
			throws Throwable {
		String clientName = (String)args[0];
		System.out.println("[MethodBeforeAdvice]How are you! Mr." + clientName + ".");
		
	}

}

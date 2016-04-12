package _aop.step07.controlFlowPointcut;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * 在方法前织入增强
 */
public class GreetingBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object obj)
			throws Throwable {
		//输出切点
		System.out.println("[Pointcut]" + obj.getClass().getName() + "." + method.getName());
		String clientName = (String)args[0];
		System.out.println("[MethodBeforeAdvice]How are you! Mr." + clientName + ".");
		
	}

}

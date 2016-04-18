package _aop.step05.staticMethodPointcut;

import java.lang.reflect.Method;
import org.springframework.aop.MethodBeforeAdvice;

public class BeforeAdvice implements MethodBeforeAdvice {
	@Override
	public void before(Method method, Object[] args, Object obj)
			throws Throwable {
		System.out.println("Advice:" + obj.getClass().getSimpleName() + "蓄力");
	}
}

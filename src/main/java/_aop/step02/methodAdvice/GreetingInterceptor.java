package _aop.step02.methodAdvice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class GreetingInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		String clientName = (String)args[0];
		System.out.println("[MethodInterceptor]How are you! Mr." + clientName + ".");
		
		Object obj = invocation.proceed(); //调用目标方法
		
		System.out.println("[MethodInterceptor]Please enjoy yourself!");
		return obj;
	}

}

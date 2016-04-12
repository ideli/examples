package _aop.step01.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PerformanceHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		long start = System.currentTimeMillis();
		Object obj = method.invoke(target, args);
		long end = System.currentTimeMillis();
		System.out.println(target.getClass().getName() + "." + method.getName() + "消耗 " + (end - start));
		return obj;
	}

	private Object target;
	
	public PerformanceHandler(Object target) {
		this.target = target;
	}
}

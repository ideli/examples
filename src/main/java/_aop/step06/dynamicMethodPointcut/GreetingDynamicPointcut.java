package _aop.step06.dynamicMethodPointcut;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

public class GreetingDynamicPointcut extends DynamicMethodMatcherPointcut {

	private static List<String> specialClientList = new ArrayList<>();
	static {
		specialClientList.add("John");
		specialClientList.add("Tom");
	}
	
	@Override
	//对方法进行动态切点检查
	public boolean matches(Method method, Class<?> clazz, Object[] args) {
		System.out.println("调用matches做动态检查");
		String clientName = (String)args[0];
		return specialClientList.contains(clientName);
	}

	//对类进行静态切点检查
	@Override
	public ClassFilter getClassFilter() {
		return new ClassFilter() {
			@Override
			public boolean matches(Class<?> clazz) {
				System.out.println("调用getClassFilter对" + clazz.getName() + "做静态检查");
				return Waiter.class.isAssignableFrom(clazz);
			}
		};
	}
	
	@Override
	//对方法进行静态切点检查
	public boolean matches(Method method, Class<?> clazz) {
		System.out.println("调用matches对" + clazz.getName() + "做静态检查");
		return "greetTo".equals(method.getName());
	}
}

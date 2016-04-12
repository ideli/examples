package _aop.step08.composablePointcut;

import java.lang.reflect.Method;

import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.ControlFlowPointcut;

public class GreetingComposablePointcut {

	public Pointcut getIntersectionPointcut() {
		ComposablePointcut cp = new ComposablePointcut();
		Pointcut pt1 = new ControlFlowPointcut(WaiterDelegate.class, "service");
		
		//三种交集运算
		//cp.intersection(ClassFilter other)
		//cp.intersection(MethodMatcher other)
		//cp.intersection(Pointcut other)
		
		//三种并集运算
		//cp.union(ClassFilter other)
		//cp.union(MethodMatcher other)
		//cp.union(Pointcut other)
		
		MethodMatcher mm = new MethodMatcher() {
			@Override
			public boolean isRuntime() {
				return false;
			}

			@Override
			public boolean matches(Method method, Class<?> clazz) {
				return "greetTo".equals(method.getName());
			}

			@Override
			public boolean matches(Method arg0, Class<?> arg1, Object[] arg2) {
				System.out.println("-----动态匹配切点-----");
				return false;
			}
		};
		
		//NameMatchMethodPointcut pt2 = new NameMatchMethodPointcut();
		//pt2.addMethodName("greetTo");
		//求交集
		return cp.intersection(pt1).intersection(mm);
	}
	
}

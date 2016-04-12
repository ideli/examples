package _aop.step03.throwsAdvice;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

/**
 * 异常抛出增强 - 要织入的增强
 */
public class UserServiceTransationManager implements ThrowsAdvice {

	//第一种写法
	//public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {}
	//第二种写法
	//public void afterThrowing(Exception ex) throws Throwable {}
	//可以定义多个不同类型Exception的方法
	
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
		System.out.println("method:" + method.getName());
		System.out.println("抛出异常:" + ex.getMessage());
		System.out.println("回滚事务");
	}
}

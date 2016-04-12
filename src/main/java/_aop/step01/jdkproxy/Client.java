package _aop.step01.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/*
 * 动态代理演示(只能为接口创建动态代理)
 */
public class Client {
	public static void main(String[] args) {
		UserService target = new UserServiceImpl();
		InvocationHandler handler = new PerformanceHandler(target);
		UserService proxy = (UserService)Proxy.newProxyInstance(target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), handler);
		proxy.addUser();
		proxy.removeUser();
	}
}

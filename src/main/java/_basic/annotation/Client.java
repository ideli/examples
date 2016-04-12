package _basic.annotation;

import java.lang.reflect.Method;

public class Client {

	public static void main(String[] args) {
		Method[] methods = UserService.class.getDeclaredMethods();
		for(Method method : methods) {
			NeedTest nt = method.getAnnotation(NeedTest.class);
			if(nt != null) {
				if(nt.value()) {
					System.out.println(method.getName() + "()需要测试");
				} else {
					System.out.println(method.getName() + "()不需要测试");
				}
			}
		}
	}
}

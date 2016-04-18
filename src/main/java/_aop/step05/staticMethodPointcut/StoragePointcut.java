package _aop.step05.staticMethodPointcut;

import java.lang.reflect.Method;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

public class StoragePointcut extends StaticMethodMatcherPointcut {

	@Override
	public boolean matches(Method method, Class<?> cls) {
		switch(methodOption) {
		case 1:
			return "chop".equals(method.getName());
		case 2:
			return "rush".equals(method.getName());
		default:
			return true;
		}
	}

	public ClassFilter getClassFilter() {

		return new ClassFilter() {
			public boolean matches(Class<?> cls) {
				switch(classOption) {
				case 1:
					return (Horseman.class.isAssignableFrom(cls));
				case 2:
					return (Swordman.class.isAssignableFrom(cls));
				default:
					return true;
				}
			}
		};
	}

	private int methodOption;
	private int classOption;

	public void setMethodOption(int methodOption) {
		this.methodOption = methodOption;
	}

	public void setClassOption(int classOption) {
		this.classOption = classOption;
	}
}

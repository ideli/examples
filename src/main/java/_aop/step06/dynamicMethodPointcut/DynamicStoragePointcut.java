package _aop.step06.dynamicMethodPointcut;

import java.lang.reflect.Method;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import _aop.step05.staticMethodPointcut.Horseman;
import _aop.step05.staticMethodPointcut.Swordman;

public class DynamicStoragePointcut extends DynamicMethodMatcherPointcut {

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
	
	@Override
	public boolean matches(Method method, Class<?> cls, Object... args) {
		return args[0].equals("Abomination");
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

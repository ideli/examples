package _generics.step02;

import java.util.Map;

public class GenericsMethod {

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Map<String, Object> data, String key) {
		Object obj = data.get(key);
		return (T)obj;
	}
}

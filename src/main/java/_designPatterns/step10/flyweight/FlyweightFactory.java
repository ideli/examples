package _designPatterns.step10.flyweight;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {

	private Map<String, Flyweight> map = new HashMap<>();
	
	public FlyweightFactory() {
		map.put("X", new ConcreteFlyweight());
		map.put("Y", new ConcreteFlyweight());
		map.put("Z", new ConcreteFlyweight());
	}
	
	public Flyweight getFlyweight(String key) {
		if(!map.containsKey(key)) {
			map.put(key, new ConcreteFlyweight());
		}
		return map.get(key);
	}
}

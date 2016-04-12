package _generics;

import java.util.HashMap;
import java.util.Map;

import _generics.step01.Animal;
import _generics.step01.Duck;
import _generics.step01.GenericsObject1;
import _generics.step01.GenericsObject2;
import _generics.step02.GenericsMethod;

public class ShowTime {

	public static void main(String[] args) {
		int demo = 2;
		if(demo == 1)
			demo1();
		else if(demo == 2)
			demo2();
	}
	
	public static void demo1() {
		GenericsObject1<String> z11 = new GenericsObject1<>();
		z11.add("test");
		//z11.add(new Object()); 编译错误
		
		GenericsObject1<Animal> z12 = new GenericsObject1<>();
		z12.add(new Animal());
		z12.add(new Duck());
		
		GenericsObject2<Duck> z22 = new GenericsObject2<>();
		z22.add(new Duck());
		
		GenericsObject2<Animal> z23 = new GenericsObject2<>();
		//GenericsObject2<Duck> z24 = z23; 编译错误
		GenericsObject2<? extends Animal> z25 = z23;
		z25.toString();
	}
	
	public static void demo2() {
		Map<String, Object> data = new HashMap<>();
		data.put("key1", "value1");
		data.put("key2", "value2");
		//两种写法
		String value = GenericsMethod.getBean(data, "key1");
		System.out.println("key1=" + value);
		value = GenericsMethod.<String>getBean(data, "key2");
		System.out.println("key2=" + value);
	}
}

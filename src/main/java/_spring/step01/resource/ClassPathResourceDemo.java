package _spring.step01.resource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ClassPathResourceDemo {

	public static void main(String[] args) {
		Resource res1 = new ClassPathResource("_spring/step01/resource/test.xml");
		System.out.println(res1.getFilename());
	}
}
